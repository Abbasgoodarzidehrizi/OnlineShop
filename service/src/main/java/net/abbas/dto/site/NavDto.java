package net.abbas.dto.site;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavDto {
    private Long id;
    private String title;
    private String link;
    private Integer orderNumber;
}
