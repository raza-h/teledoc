package tele.doc.project.systems.regloc;

import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Patient;
import tele.doc.project.domain.Status;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.PatientRepository;

public class DoctorLogin implements LoginSystem{
    private final DoctorRepository pr;
    String username;
    String password;

    public DoctorLogin(String username, String password, DoctorRepository pr)
    {
        this.username = username;
        this.password = password;
        this.pr = pr;
    }

    @Override
    public boolean verifyUser(String username, String password)
    {
        Doctor p = pr.findByUsername(username);
        if (p != null && password.equals(p.getPassword()))
        {
            if (p.isStatus() == Status.pending)
            {
                Visitor.errorMessage = "Account Verification Pending";
                return false;
            }

            else if (p.isStatus() == Status.rejected || p.isStatus() == Status.removed)
            {
                Visitor.errorMessage = "Account is no longer accessible.";
                return false;
            }
        }

        else
        {
            Visitor.errorMessage = "Wrong username or password";
            return false;
        }

        Visitor.currentUser = username;
        return true;
    }

    @Override
    public boolean login()
    {
        if (verifyUser(username, password))
        {
            Visitor.userType = "doctor";
            return true;
        }

        return false;
    }
}
