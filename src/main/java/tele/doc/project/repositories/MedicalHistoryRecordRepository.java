package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.MedicalHistoryRecord;

public interface MedicalHistoryRecordRepository  extends CrudRepository<MedicalHistoryRecord, Long> {
}
