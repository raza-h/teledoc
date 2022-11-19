package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.MedicalHistoryRecord;
import tele.doc.project.domain.Patient;

import java.util.Set;

public interface MedicalHistoryRecordRepository  extends CrudRepository<MedicalHistoryRecord, Long> {
    Set<MedicalHistoryRecord> findByPatient(Patient p);
}
