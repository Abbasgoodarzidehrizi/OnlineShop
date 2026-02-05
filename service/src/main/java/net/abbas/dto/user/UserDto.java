package net.abbas.dto.user;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.user.Customer;
import net.abbas.dataaccess.entity.user.Role;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String mobile;
    private String password;
    private String email;
    private LocalDateTime registerDate;
    private Boolean enabled = true;
    private Set<RoleDto> roles;
    private CustomerDto customer;
}
