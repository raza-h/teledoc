package tele.doc.project.systems.regloc;

import tele.doc.project.domain.Patient;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.PatientRepository;

public class PatientLogin implements LoginSystem{

    private final PatientRepository pr;
    String username;
    String password;

    public PatientLogin(String username, String password, PatientRepository pr)
    {
        this.username = username;
        this.password = password;
        this.pr = pr;
    }

    @Override
    public boolean verifyUser(String username, String password)
    {
        Patient p = pr.findByUsername(username);
        if (p == null || !password.equals(p.getPassword()))
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
            Visitor.userType = "patient";
            return true;
        }

        return false;
    }

}
