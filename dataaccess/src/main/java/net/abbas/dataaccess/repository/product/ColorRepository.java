package net.abbas.dataaccess.repository.product;

import net.abbas.dataaccess.entity.product.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}
