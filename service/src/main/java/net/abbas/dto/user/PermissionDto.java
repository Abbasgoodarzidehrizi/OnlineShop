package net.abbas.dto.user;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private Long id;
    private String name;
    private String description;
}

