package tele.doc.project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Status;

import java.util.Set;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findByUsername(String username);
    Set<Doctor> findByStatus(Status status);
    Doctor findByEmail(String email);

    @Query("SELECT distinct d.Specialization from Doctor d")
    Set<String> findAllBySpecialization();

}
