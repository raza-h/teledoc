package tele.doc.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tele.doc.project.domain.Appointment;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Patient;
import tele.doc.project.domain.Status;

import java.util.Set;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    Set<Appointment> findByDoctor(Doctor d);
    Set<Appointment> findByPatient(Patient p);
    Set<Appointment> findByDoctorAndStatus(Doctor d, Status s);
    Set<Appointment> findByPatientAndStatus(Patient p,Status s);
    Set<Appointment> findByPatientAndDoctor(Patient p, Doctor d);
}
