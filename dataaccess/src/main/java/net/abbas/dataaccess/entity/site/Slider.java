package net.abbas.dataaccess.entity.site;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.file.File;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String link;

    @Column(nullable = false)
    private Boolean enabled = true;

    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(nullable = false)
    private File image;

}
