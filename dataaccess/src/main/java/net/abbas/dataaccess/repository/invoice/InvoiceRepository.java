package net.abbas.dataaccess.repository.invoice;

import net.abbas.dataaccess.entity.file.File;
import net.abbas.dataaccess.entity.order.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> readAllByUser_Id(Long id);
}
