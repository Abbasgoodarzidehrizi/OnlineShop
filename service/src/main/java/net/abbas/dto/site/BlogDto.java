package net.abbas.dto.site;

import lombok.*;
import net.abbas.dataaccess.entity.file.File;
import net.abbas.dataaccess.enums.BlogStatus;
import net.abbas.dto.file.FileDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private Long id;
    private String title;
    private String subtitle;
    private LocalDateTime publishDate;
    private Long visitCount;
    private BlogStatus status;
    private FileDto image;
    private String description;
}
