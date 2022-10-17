package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Patient;

public interface PatientRepository  extends CrudRepository<Patient, Long> {
}
