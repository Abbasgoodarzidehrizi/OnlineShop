package net.abbas.dataaccess.repository.site;

import net.abbas.dataaccess.entity.order.InvoiceItem;
import net.abbas.dataaccess.entity.site.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("""

from Blog where status=net.abbas.dataaccess.enums.BlogStatus.published
and publishDate <= current_date
order by publishDate desc

""")
    Page<Blog> findAllPublished(Pageable pageable);
}
