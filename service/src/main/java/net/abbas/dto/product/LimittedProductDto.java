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
public class LimittedProductDto {
    private Long id;
    private String title;
    private Long price;
    private Long visitCount;
    private LocalDateTime addDate;
    private FileDto image;
    private Set<ProductDto> products;
    private Set<SizeDto> sizes;
    private ProductCategoryDto category;
}
