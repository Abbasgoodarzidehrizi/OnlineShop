package net.abbas.dataaccess.repository.site;

import net.abbas.dataaccess.entity.site.Content;
import net.abbas.dataaccess.entity.site.Nav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface NavRepository extends JpaRepository<Nav, Long> {
    List<Nav> findAllByEnabledIsTrueOrderByOrderNumberAsc();

    Optional<Nav> findFirstByOrderNumberLessThanOrderByOrderNumberDesc(Integer orderNumber);
    Optional<Nav> findFirstByOrderNumberGreaterThanOrderByOrderNumberAsc(Integer orderNumber);
}
