package net.abbas.dto.product;

import lombok.*;
import net.abbas.dto.file.FileDto;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Long price;
    private Long visitCount;
    private LocalDateTime addDate;
    private FileDto image;
    private Set<ColorDto> products;
    private Set<SizeDto> sizes;
    private ProductCategoryDto category;
    private Boolean enabled = true;
    private Boolean exists = true;
    private String description;
}
