package net.abbas.dataaccess.entity.product;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.file.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@Column(length = 500, nullable = false)
private String title;

@Column(length = 100, nullable = false)
private String description;

private Boolean enabled = true;

@ManyToOne
@JoinColumn(nullable = false)
private File images;
}
