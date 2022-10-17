package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Admin;

public interface AdminRepository extends CrudRepository <Admin, Long> {
}
