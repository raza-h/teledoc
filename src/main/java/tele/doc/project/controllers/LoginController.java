package tele.doc.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.AdminRepository;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.PatientRepository;
import tele.doc.project.repositories.SuperAdminRepository;
import tele.doc.project.systems.regloc.AdminLogin;
import tele.doc.project.systems.regloc.DoctorLogin;
import tele.doc.project.systems.regloc.PatientLogin;
import tele.doc.project.systems.regloc.SuperLogin;

@Controller
public class LoginController {
    private final DoctorRepository dr;
    private final PatientRepository pr;
    private final AdminRepository ar;
    private final SuperAdminRepository sr;

    LoginController(DoctorRepository dr, PatientRepository pr, AdminRepository ar, SuperAdminRepository sr)
    {
        this.dr= dr;
        this.pr = pr;
        this.ar = ar;
        this.sr = sr;
    }

    @PostMapping(value = "/doctor-login", consumes = {"*/*"})
    public String LogDoc(@ModelAttribute("doctor") Doctor doctor)
    {
        DoctorLogin pl = new DoctorLogin(doctor.getUsername(), doctor.getPassword(), dr);
        System.out.println(pl.login());
        if (pl.login())
        {
            return "redirect:/doctor";
        }

        return "redirect:/problem";
    }

    @PostMapping(value = "/patient-login", consumes = {"*/*"})
    public String LogDoc(@ModelAttribute("patient") Patient patient)
    {
        PatientLogin pl = new PatientLogin(patient.getUsername(), patient.getPassword(), pr);
        System.out.println(pl.login());
        if (pl.login())
        {
            return "redirect:/patient";
        }

        return "redirect:/problem";
    }

    @PostMapping(value = "/admin-login", consumes = {"*/*"})
    public String LogAdmin(@ModelAttribute("admin") Admin admin)
    {
        AdminLogin pl = new AdminLogin(admin.getUsername(), admin.getPassword(), ar);
        System.out.println(pl.login());
        if (pl.login())
        {
            return "redirect:/admin";
        }

        return "redirect:/problem";
    }

    @PostMapping(value = "/super-login", consumes = {"*/*"})
    public String LogAdmin(@ModelAttribute("superadmin") SuperAdmin admin)
    {
        SuperLogin pl = new SuperLogin(admin.getUsername(), admin.getPassword(), sr);
        System.out.println(pl.login());
        if (pl.login())
        {
            return "redirect:/superadmin";
        }

        return "redirect:/problem";
    }

}
