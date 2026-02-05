package net.abbas.dto.site;

import lombok.*;
import net.abbas.dto.file.FileDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LimitedBlogDto {
    private Long id;
    private String title;
    private String subtitle;
    private LocalDateTime publishDate;
    private Long visitCount;
    private FileDto image;
}
