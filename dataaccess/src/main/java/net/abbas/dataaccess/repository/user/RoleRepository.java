package net.abbas.dataaccess.repository.user;

import net.abbas.dataaccess.entity.user.Permission;
import net.abbas.dataaccess.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findFirstByNameEqualsIgnoreCase(String name);
}
