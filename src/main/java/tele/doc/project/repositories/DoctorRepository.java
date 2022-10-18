package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Doctor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
}
