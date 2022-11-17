package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.EducationRecord;
import tele.doc.project.domain.Patient;

public interface EducationRecordRepository extends CrudRepository<EducationRecord, Long> {
}
