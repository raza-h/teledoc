package tele.doc.project.systems.regloc;

import tele.doc.project.domain.Admin;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.AdminRepository;

public class AdminLogin implements LoginSystem{
    private final AdminRepository pr;
    String username;
    String password;

    public AdminLogin(String username, String password, AdminRepository pr)
    {
        this.username = username;
        this.password = password;
        this.pr = pr;
    }

    @Override
    public boolean verifyUser(String username, String password)
    {
        Admin p = pr.findByUsername(username);
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
            Visitor.userType = "admin";
            return true;
        }

        return false;
    }

}
