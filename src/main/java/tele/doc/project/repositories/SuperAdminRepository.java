package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.SuperAdmin;

public interface SuperAdminRepository extends CrudRepository<SuperAdmin, Long> {
    SuperAdmin findByUsername(String username);
}
