package net.abbas.dataaccess.repository.product;

import net.abbas.dataaccess.entity.product.Color;
import net.abbas.dataaccess.entity.product.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findAllByEnabledIsTrue();


}
