package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findByUsername(String username);
    Set<Doctor> findByStatus(Status status);
}
