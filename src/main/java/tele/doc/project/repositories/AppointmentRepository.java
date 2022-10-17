package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
