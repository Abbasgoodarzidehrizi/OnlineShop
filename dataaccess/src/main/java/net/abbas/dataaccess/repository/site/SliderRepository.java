package net.abbas.dataaccess.repository.site;

import net.abbas.dataaccess.entity.site.Nav;
import net.abbas.dataaccess.entity.site.Slider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface SliderRepository extends JpaRepository<Slider, Long> {

    @Query("""
    select orderNumber from Slider order by orderNumber desc limit 1
""")
   Integer firstLastOrderNumber();

    Page<Slider> findAllByEnabledIsTrueOrderByOrderNumberAsc(Pageable pageable);

    Page<Slider> findAllByEnabledIsTrue(Pageable pageable);

    List<Slider> findAllByEnabledIsTrueOrderByOrderNumberAsc();

    Optional<Slider> findFirstByOrderNumberLessThanOrderByOrderNumberDesc(Integer id);
    Optional<Slider> findFirstByOrderNumberGreaterThanOrderByOrderNumberAsc(Integer id);
}
