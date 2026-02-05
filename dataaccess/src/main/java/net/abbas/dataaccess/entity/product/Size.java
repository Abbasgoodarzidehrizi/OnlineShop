package net.abbas.dataaccess.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Size {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@Column(length = 500, nullable = false)
private String title;

@Column(length = 1000, nullable = false)
private String description;
}
