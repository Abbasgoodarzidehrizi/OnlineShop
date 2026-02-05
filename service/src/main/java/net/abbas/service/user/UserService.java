package net.abbas.service.user;

import lombok.SneakyThrows;
import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.user.Customer;
import net.abbas.dataaccess.entity.user.Role;
import net.abbas.dataaccess.entity.user.User;
import net.abbas.dataaccess.repository.user.CustomerRepository;
import net.abbas.dataaccess.repository.user.RoleRepository;
import net.abbas.dataaccess.repository.user.UserRepository;
import net.abbas.dto.user.LimitedUserDto;
import net.abbas.dto.user.LoginDto;
import net.abbas.dto.user.UserDto;
import net.abbas.utill.JwtUtill;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;
    private final JwtUtill jwtUtill;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, CustomerRepository customerRepository, ModelMapper mapper, JwtUtill jwtUtill, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.jwtUtill = jwtUtill;
        this.roleRepository = roleRepository;
    }


    public UserDto readByUsername(String username) {
        User user = userRepository.findFirstByUsername(username).orElseThrow(NotFoundException::new);
        return mapper.map(user, UserDto.class);
    }

    public LimitedUserDto login(LoginDto loginDto) {
        User user = userRepository.findFirstByUsernameEqualsIgnoreCaseAndPassword(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(NotFoundException::new);
        if (!user.getEnabled()) {
            throw new ValidationExceptions("User is not enabled");
        }

        LimitedUserDto map = mapper.map(user, LimitedUserDto.class);
        map.setToken(jwtUtill.generateToken(map.getUsername()));
        return map;
    }

    @SneakyThrows
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return mapper.map(user, UserDto.class);
    }

    public UserDto create(UserDto dto) {
        checkFullValidations(dto);
        Customer customer = customerRepository.save(mapper.map(dto.getCustomer(), Customer.class));
        User user = mapper.map(dto, User.class);
        user.setCustomer(customer);
        user.setEnabled(true);
        user.setRegisterDate(LocalDateTime.now());
        user.setPassword(dto.getPassword());
        Optional<Role> user1 = roleRepository.findFirstByNameEqualsIgnoreCase("user");
        if (user1.isPresent()) {
            HashSet<Role> userRole = new HashSet<>();
            userRole.add(user1.get());
            user.setRoles(userRole);
        }
        User savedUser = userRepository.save(user);
        return mapper.map(savedUser, UserDto.class);
    }
    public void checkFullValidations(UserDto dto){
    if (dto.getCustomer()== null){
        throw new ValidationExceptions("Customer is required");
    }
        if (dto.getCustomer().getFirstname() == null || dto.getCustomer().getFirstname().isEmpty()) {
            throw new ValidationExceptions("Please enter firstname");
        }
        if (dto.getCustomer().getLastname() == null || dto.getCustomer().getLastname().isEmpty()) {
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
        if (dto.getCustomer().getTel() == null || dto.getCustomer().getTel().isEmpty()) {
            throw new ValidationExceptions("Please enter tel");
        }
        if (dto.getCustomer().getAddress() == null || dto.getCustomer().getAddress().isEmpty()) {
            throw new ValidationExceptions("Please enter address");
        }
        if (dto.getCustomer().getPostalCode() == null || dto.getCustomer().getPostalCode().isEmpty()) {
            throw new ValidationExceptions("Please enter postalCode");
        }
    }
}
