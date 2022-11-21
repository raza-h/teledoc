package tele.doc.project.systems.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tele.doc.project.domain.Appointment;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Status;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.AppointmentRepository;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.SuperAdminRepository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class DoctorRemovalSystem extends SuperAdminVerification{

    String username;
    @Autowired DoctorRepository dr;
    @Autowired
    AppointmentRepository ar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkStatus()
    {
        if (dr.findByUsername(username).getRating() >= 3)
        {
            Visitor.errorMessage="Rating greater than 3.0, cannot be removed";
            return false;
        }

        Set<Appointment> ap = ar.findByDoctor(dr.findByUsername(username));
        Iterator<Appointment> it = ap.iterator();

        while(it.hasNext())
        {
            Appointment a = it.next();
            if(a.getStatus() != Status.removed)
            {
                Visitor.errorMessage="Doctor has active appointments";
                return false;
            }
        }

        return true;
    }

    public boolean changeStatus(String password){
        if(!verifyUser(Visitor.currentUser, password))
        {
            Visitor.errorMessage="Wrong Password";
            return false;
        }

        Doctor d = dr.findByUsername(username);
        d.setStatus(Status.removed);
        dr.save(d);
        return true;
    }


    public DoctorRemovalSystem(SuperAdminRepository sr, DoctorRepository dr)
    {
        super(sr);
    }
}
