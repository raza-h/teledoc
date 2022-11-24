package tele.doc.project.systems.others;

import tele.doc.project.domain.Appointment;
import tele.doc.project.repositories.AppointmentRepository;

public class DecideAppointmentSystem {
    private final AppointmentRepository ar;

    public DecideAppointmentSystem(AppointmentRepository ar)
    {
        this.ar = ar;
    }

    public void save(Appointment a)
    {
        ar.save(a);
    }



}
