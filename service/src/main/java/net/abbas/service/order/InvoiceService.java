package net.abbas.service.order;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.order.Invoice;
import net.abbas.dataaccess.entity.order.InvoiceItem;
import net.abbas.dataaccess.enums.OrderStatus;
import net.abbas.dataaccess.repository.invoice.InvoiceItemRepository;
import net.abbas.dataaccess.repository.invoice.InvoiceRepository;
import net.abbas.dataaccess.repository.product.ProductRepository;
import net.abbas.dto.order.InvoiceDto;
import net.abbas.dto.product.ProductDto;
import net.abbas.service.base.CreateService;
import net.abbas.service.base.HasValidation;
import net.abbas.service.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService implements CreateService<InvoiceDto> , HasValidation<InvoiceDto> {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final ProductService service;
    private final ModelMapper modelMapper;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository, ProductRepository productRepository, ProductService service, ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.service = service;
        this.modelMapper = modelMapper;
    }
    @Override
    public InvoiceDto create(InvoiceDto invoiceDto) {
        checkValidations(invoiceDto);
        Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
        invoice.setCreateDate(LocalDate.from(LocalDateTime.now()));
        invoice.setPayedDate(null);
        invoice.setStatus(OrderStatus.InProgress);
        Long totlaAmount = 0l;
        if (invoice.getInvoiceItem() != null) {
            for (InvoiceItem ii : invoice.getInvoiceItem()) {
                ProductDto product = service.readProductById((ii.getProduct().getId()));
                totlaAmount += product.getPrice() * ii.getQuantity();
            }
        }

        invoice.setTotalAmount(totlaAmount);
        Invoice save = invoiceRepository.save(invoice);
        return modelMapper.map(save, InvoiceDto.class);
    }

    @Override
    public void checkValidations(InvoiceDto invoiceDto) {
        if (invoiceDto.getInvoiceItem() == null) {
            throw new ValidationExceptions("invoiceItem cannot be null");
        }
        if (invoiceDto.getStatus().equals("")) {
            throw new ValidationExceptions("status cannot be empty");
        }
        if (invoiceDto.getCreateDate() == null || invoiceDto.getCreateDate().equals("")) {
            throw new ValidationExceptions("createDate cannot be empty");
        }

        if (invoiceDto.getCreateDate() == null || invoiceDto.getCreateDate().equals("")) {
            throw new ValidationExceptions("createDate cannot be empty");
        }
        if (invoiceDto.getInvoiceItem() == null) {
            throw new ValidationExceptions("invoiceItem cannot be null");
        }
    }
    public List<InvoiceDto> readByUser(Long id){
        return invoiceRepository.readAllByUser_Id(id).stream().map(x -> modelMapper.map(x, InvoiceDto.class)).toList();
    }
    public InvoiceDto read(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(invoice,InvoiceDto.class);
    }
}
