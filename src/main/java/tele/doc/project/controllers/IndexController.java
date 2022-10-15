package tele.doc.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;

import java.awt.*;

@Controller
public class IndexController {
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
}


