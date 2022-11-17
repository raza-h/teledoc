package tele.doc.project.systems.regloc;

import tele.doc.project.domain.Admin;
import tele.doc.project.domain.SuperAdmin;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.AdminRepository;
import tele.doc.project.repositories.SuperAdminRepository;

public class SuperLogin implements LoginSystem{
    private final SuperAdminRepository pr;
    String username;
    String password;

    public SuperLogin(String username, String password, SuperAdminRepository pr)
    {
        this.username = username;
        this.password = password;
        this.pr = pr;
    }

    @Override
    public boolean verifyUser(String username, String password)
    {
        SuperAdmin p = pr.findByUsername(username);
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
            Visitor.userType = "superAdmin";
            return true;
        }

        return false;
    }
}
