package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.EducationRecord;
import tele.doc.project.domain.Patient;

import java.util.Set;

public interface EducationRecordRepository extends CrudRepository<EducationRecord, Long> {
    Set<EducationRecord> findByDoctor(Doctor d);
}
