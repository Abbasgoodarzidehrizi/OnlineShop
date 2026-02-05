package net.abbas.dataaccess.entity.site;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String keyName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String valueContent;


}
