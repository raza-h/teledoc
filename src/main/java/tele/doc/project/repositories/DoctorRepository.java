package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Doctor;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
}
