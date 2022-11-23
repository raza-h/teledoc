package tele.doc.project.systems.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tele.doc.project.domain.Appointment;
import tele.doc.project.repositories.AppointmentRepository;
public class CancelAppointmentSystem {
    private final AppointmentRepository r;
    public CancelAppointmentSystem(AppointmentRepository r)
    {
        this.r = r;
    }

    public void cancelAppointment(Appointment a)
    {
        r.delete(a);
    }

}
