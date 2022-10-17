package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Education;

public interface EducationRepository  extends CrudRepository<Education, Long> {
}
