package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Review;

import java.util.Set;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    Set<Review> findByDoctor(Doctor d);
}
