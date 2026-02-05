package net.abbas.dataaccess.repository.user;

import net.abbas.dataaccess.entity.user.Customer;
import net.abbas.dataaccess.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
