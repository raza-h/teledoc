package tele.doc.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.AdminRepository;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.PatientRepository;
import tele.doc.project.repositories.SuperAdminRepository;

import java.awt.*;

@Controller
public class IndexController {

    private final DoctorRepository dr;
    private final PatientRepository pr;
    private final AdminRepository ar;
    private final SuperAdminRepository sr;

    public IndexController(DoctorRepository dr, PatientRepository pr, AdminRepository ar, SuperAdminRepository sr) {
        this.pr = pr;
        this.ar = ar;
        this.sr = sr;
        this.dr = dr;
    }

    @RequestMapping("/")
    public String index()
    {
        return "all/home";
    }

    @RequestMapping("/doctor-login")
    public String doctorLogin(Model model)
    {
        model.addAttribute("doctor", new Doctor());
        return "doctor/login";
    }

    @RequestMapping("/patient-login")
    public String patientLogin(Model model)
    {
        model.addAttribute("patient", new Patient());
        return "patient/login";
    }

    @RequestMapping("/admin-login")
    public String adminLogin(Model model)
    {
        model.addAttribute("admin", new Admin());
        return "admin/login";
    }
    @RequestMapping("/super-login")
    public String superAdminLogin(Model model)
    {
        model.addAttribute("superAdmin", new SuperAdmin());
        return "superadmin/login";
    }

    @RequestMapping("/doctor-register")
    public String doctorRegister()
    {
        return "doctor/register";
    }

    @RequestMapping("/patient-register")
    public String patientRegister()
    {
        return "patient/register";
    }

    @RequestMapping("/doctor")
    public String doctorHome(Model model)
    {
        model.addAttribute("doctor", dr.findByUsername(Visitor.currentUser));
        return "doctor/home";
    }

    @RequestMapping("/patient")
    public String patientHome(Model model)
    {
        model.addAttribute("patient", pr.findByUsername(Visitor.currentUser));
        return "patient/home";
    }

    @RequestMapping("/admin")
    public String adminHome(Model model)
    {
        System.out.println(Visitor.currentUser);
        model.addAttribute("admin", ar.findByUsername(Visitor.currentUser));
        return "admin/home";
    }

    @RequestMapping("/superadmin")
    public String superHome(Model model)
    {
        model.addAttribute("superadmin", sr.findByUsername(Visitor.currentUser));
        return "superadmin/home";
    }

    @RequestMapping("/problem")
    public String errorAll(Model model)
    {
        model.addAttribute("errorMessage", Visitor.errorMessage);
        return "all/error";
    }
}


