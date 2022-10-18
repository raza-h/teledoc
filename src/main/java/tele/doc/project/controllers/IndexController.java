package tele.doc.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tele.doc.project.repositories.DoctorRepository;

import java.awt.*;

@Controller
public class IndexController {

    private final DoctorRepository dr;

    public IndexController(DoctorRepository dr) {
        this.dr = dr;
    }

    @RequestMapping("/")
    public String index()
    {
        return "all/home";
    }

    @RequestMapping("/doctor-login")
    public String doctorLogin()
    {
        return "doctor/login";
    }

    @RequestMapping("/patient-login")
    public String patientLogin()
    {
        return "patient/login";
    }

    @RequestMapping("/admin-login")
    public String adminLogin()
    {
        return "admin/login";
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
    public String doctorHome(@RequestParam(name="username", required = true, defaultValue = "ab") String username, Model model)
    {
        model.addAttribute("doctor", dr.findByUsername(username));
        return "doctor/home";
    }

}


