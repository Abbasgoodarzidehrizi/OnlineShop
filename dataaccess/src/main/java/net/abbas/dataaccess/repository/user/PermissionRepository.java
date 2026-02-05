package net.abbas.dataaccess.repository.user;

import net.abbas.dataaccess.entity.site.Blog;
import net.abbas.dataaccess.entity.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
