package net.abbas.dto.site;

import lombok.*;
import net.abbas.dto.file.FileDto;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SliderDto {
    private Long id;
    private String title;
    private String link;
    private Integer orderNumber;
    private FileDto image;
}
