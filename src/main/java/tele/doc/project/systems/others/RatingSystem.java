package tele.doc.project.systems.others;

import org.springframework.aop.target.AbstractPoolingTargetSource;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.AppointmentRepository;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.PatientRepository;
import tele.doc.project.repositories.ReviewRepository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RatingSystem extends PatientVerification{
    private final ReviewRepository rr;
    private final DoctorRepository dr;

    private final PatientRepository pr;

    private final AppointmentRepository ar;
    String username;
    public RatingSystem(ReviewRepository rr, DoctorRepository dr, PatientRepository pr, AppointmentRepository ar)
    {
        this.rr = rr;
        this.dr = dr;
        this.pr = pr;
        this.ar = ar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public boolean check(Doctor d1, Doctor d2)
    {
        return true;
    }

    public boolean checkAppointment()
    {
        Patient p = pr.findByUsername(Visitor.currentUser);
        Doctor doc = dr.findByUsername(username);
        System.out.println(ar.findByPatientAndDoctor(p, doc).size());
        Set<Appointment> apl = ar.findByPatientAndDoctor(p,doc);
        Iterator<Appointment> it = apl.iterator();
        Set<Appointment> apl2 = new HashSet<>();

        while(it.hasNext())
        {
            Appointment a = it.next();
            if (a.getStatus() == Status.removed)
            {
                apl2.add(a);
            }
        }

        if(apl2.size() > 0)
        {
            return true;
        }

        return false;
    }

    public void enterRating(Review r)
    {
        System.out.println(r);
        Patient p = pr.findByUsername(Visitor.currentUser);
        Doctor doc = dr.findByUsername(username);
        doc.getReviews().add(r);
        doc.calcRating();
        rr.save(r);
        dr.save(doc);
    }
}
