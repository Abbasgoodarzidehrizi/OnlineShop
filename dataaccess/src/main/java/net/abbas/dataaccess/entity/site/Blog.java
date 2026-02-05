package net.abbas.dataaccess.entity.site;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.file.File;
import net.abbas.dataaccess.enums.BlogStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1000, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String subTitle;

    private Boolean enabled = true;

    private LocalDateTime publishDate;
    private BlogStatus status;
    private Long visitCount;
    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(nullable = false)
    private File image;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;


}
