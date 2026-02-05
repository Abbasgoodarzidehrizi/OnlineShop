package net.abbas.dataaccess.entity.product;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.file.File;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private Long price;
    private Boolean enabled;
    private Boolean existed;
    private Long visitCount;
    private LocalDateTime addDate;

    @ManyToMany
    @JoinTable(name = "product_colors",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> products;

    @ManyToMany
    @JoinTable(name = "product_size,",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private Set<Size> size;

    @ManyToOne
    private ProductCategory productCategories;

    @ManyToOne
    @JoinColumn(nullable = false)
    private File image;

}
