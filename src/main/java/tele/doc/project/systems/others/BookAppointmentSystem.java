package tele.doc.project.systems.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import tele.doc.project.domain.Appointment;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Status;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.AppointmentRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BookAppointmentSystem {
    String username;

    private final AppointmentRepository ar;

    public BookAppointmentSystem(AppointmentRepository ar)
    {
        this.ar = ar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkDate(Doctor d, Date date){
        Set<Appointment> a = ar.findByDoctor(d);
        Set<Appointment> filtered = new HashSet<>();
        Iterator<Appointment> fit = a.iterator();
        while(fit.hasNext())
        {
            Appointment ap = fit.next();
            if (ap.getStatus()== Status.approved || ap.getStatus()==Status.pending)
            {
                filtered.add(ap);
            }
        }
        Iterator<Appointment> it = filtered.iterator();

        while(it.hasNext()){
            Appointment ap = it.next();
            if (date.getTime() >= ap.getDate().getTime() && date.getTime() <= (ap.getDate().getTime() + 1800000))
            {
                Visitor.errorMessage = "Slot already filled. Please check the appointments table before making an appointment";
                return false;
            }
        }
        return true;
    }
}
