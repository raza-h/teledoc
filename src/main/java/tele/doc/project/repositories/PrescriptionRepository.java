package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Patient;
import tele.doc.project.domain.Prescription;

public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {

}
