package net.abbas.dataaccess.repository.product;

import net.abbas.dataaccess.entity.product.Color;
import net.abbas.dataaccess.entity.product.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
}
