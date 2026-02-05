package net.abbas.dto.user;

import lombok.*;
import net.abbas.dataaccess.entity.user.Role;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimitedUserDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private Boolean enabled;
    private String token;
}
