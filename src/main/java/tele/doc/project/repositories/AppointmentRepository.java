package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Appointment;
import tele.doc.project.domain.Doctor;

import java.util.Set;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    Set<Appointment> findByDoctor(Doctor d);
}
