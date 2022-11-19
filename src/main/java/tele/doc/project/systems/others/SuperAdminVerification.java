package tele.doc.project.systems.others;

import tele.doc.project.domain.SuperAdmin;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.SuperAdminRepository;

public class SuperAdminVerification implements VerificationSystem{
    protected final SuperAdminRepository sr;

    SuperAdminVerification(SuperAdminRepository sr)
    {
        this.sr = sr;
    }


    public boolean verifyUser(String username, String password)
    {
        SuperAdmin sa = sr.findByUsername(username);

        if (sa.getPassword().equals(password))
        {
            return true;
        }

        Visitor.errorMessage="Wrong Password";
        return false;
    }
}
