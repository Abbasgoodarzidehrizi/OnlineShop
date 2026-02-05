package net.abbas.service.payment.provider.zarinpal;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.order.Invoice;
import net.abbas.dataaccess.entity.payment.Payment;
import net.abbas.dataaccess.entity.payment.Transaction;
import net.abbas.dataaccess.entity.user.User;
import net.abbas.dataaccess.repository.payment.PaymentRepository;
import net.abbas.dataaccess.repository.payment.TransactionRepository;
import net.abbas.dto.order.InvoiceDto;
import net.abbas.dto.order.InvoiceItemDto;
import net.abbas.dto.payment.GoToPaymentDto;
import net.abbas.dto.payment.PaymentDto;
import net.abbas.dto.product.ColorDto;
import net.abbas.dto.product.ProductDto;
import net.abbas.dto.product.ProductDto;
import net.abbas.dto.product.SizeDto;
import net.abbas.dto.user.CustomerDto;
import net.abbas.dto.user.LimitedUserDto;
import net.abbas.dto.user.UserDto;
import net.abbas.service.order.InvoiceService;
import net.abbas.service.payment.provider.zarinpal.provider.ZarinPalProvider;
import net.abbas.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {
    private final UserService userService;
    private final InvoiceService invoiceService;
    private final ModelMapper mapper;
    private final ZarinPalProvider zarinPalProvider;
    private final PaymentRepository paymentRepository;
    private final TransactionRepository trxRepository;

    @Autowired
    public PaymentService(UserService userService, InvoiceService invoiceService, ModelMapper mapper, ZarinPalProvider zarinPalProvider, PaymentRepository paymentRepository, TransactionRepository trxRepository) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.mapper = mapper;
        this.zarinPalProvider = zarinPalProvider;
        this.paymentRepository = paymentRepository;
        this.trxRepository = trxRepository;
    }
    @Transactional
    public String goToPayment(GoToPaymentDto dto) throws Exception {

        /*
        *   1- Validation
            2- Create New User
            3- Create New Invoice + Invoice Items
            4- Calculate Total Amount
            5- Create New Transaction
            6- Send Request To Bank IPG
            7- Receive IPG Response
            8- Update Transaction => Save IPG Token
            9- Redirect User to IPG URL
        * */

        checkValidation(dto);
        UserDto user = userService.create(UserDto.builder()
                .username(dto.getUsername())
                .mobile(dto.getMobile())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .customer(CustomerDto.builder()
                        .firstname(dto.getFirstname())
                        .lastname(dto.getLastname())
                        .tel(dto.getTel())
                        .address(dto.getAddress())
                        .postalCode(dto.getPostalCode())
                        .build())
                .build());


        InvoiceDto invoice = invoiceService.create(InvoiceDto.builder()
                .user(LimitedUserDto.builder().id(user.getId()).build())
                .invoiceItem(dto.getBasketItem().stream().map(x -> InvoiceItemDto.builder()
                        .product(ProductDto.builder().id(x.getProductId()).build())
                        .color(ColorDto.builder().id(x.getColorId()).build())
                        .size(SizeDto.builder().id(x.getSizeId()).build())
                        .quantity(x.getQuantity())
                        .build()).toList())
                .build());


        Payment gateway = paymentRepository.findFirstByNameEqualsIgnoreCase(dto.getGateway().toString()).orElseThrow(NotFoundException::new);
        Transaction trx = Transaction.builder()
                .amount(invoice.getTotalAmount())
                .payment(gateway)
                .description(invoice.getId() + "_" + invoice.getTotalAmount())
                .customer(mapper.map(user, User.class))
                .invoice(mapper.map(invoice, Invoice.class))
                .build();
        String result = "";
        switch (dto.getGateway()) {
            case Zarinpal -> {
                result = zarinPalProvider.goToPayment(trx);
            }
            case CardToCard -> {
            }
            case MellatBank -> {
            }
            case TejaratBank -> {
            }
            case PasargadBank -> {
            }
        }
        trxRepository.save(trx);
        return result;
    }

    public String verify(String authority, String status) throws NotFoundException {
        if (status == null || status.isEmpty() || status.equalsIgnoreCase("NOK")) {
            return "NOK";
        }

        if (status.equalsIgnoreCase("OK")) {
            Transaction trx = trxRepository.findFirstByAuthorityEqualsIgnoreCase(authority).orElseThrow(NotFoundException::new);
            Transaction verifiedTrx = zarinPalProvider.verifyPayment(trx);
            trxRepository.save(verifiedTrx);
            return "OK";
        }

        return "NOK";
    }

    private static void checkValidation(GoToPaymentDto dto) throws ValidationExceptions {
        if (dto.getGateway() == null) {
            throw new ValidationExceptions("Please select payment gateway");
        }
        if (dto.getFirstname() == null || dto.getFirstname().isEmpty()) {
            throw new ValidationExceptions("Please enter firstname");
        }
        if (dto.getLastname() == null || dto.getLastname().isEmpty()) {
            throw new ValidationExceptions("Please enter lastname");
        }
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            throw new ValidationExceptions("Please enter username");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new ValidationExceptions("Please enter password");
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new ValidationExceptions("Please enter email");
        }
        if (dto.getMobile() == null || dto.getMobile().isEmpty()) {
            throw new ValidationExceptions("Please enter mobile");
        }
        if (dto.getTel() == null || dto.getTel().isEmpty()) {
            throw new ValidationExceptions("Please enter tel");
        }
        if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
            throw new ValidationExceptions("Please enter address");
        }
        if (dto.getPostalCode() == null || dto.getPostalCode().isEmpty()) {
            throw new ValidationExceptions("Please enter postalCode");
        }
        if (dto.getBasketItem() == null || dto.getBasketItem().isEmpty()) {
            throw new ValidationExceptions("Please add at least one product to your basket");
        }
    }

    public List<PaymentDto> readAllGateways() {
        return paymentRepository.findAllByEnableIsTrue().stream().map(x -> mapper.map(x, PaymentDto.class)).toList();
    }
}
