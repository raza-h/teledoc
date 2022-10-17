package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
