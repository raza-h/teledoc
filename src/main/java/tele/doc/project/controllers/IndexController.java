package tele.doc.project.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.*;

import java.awt.*;

@Controller
public class IndexController {

    private final DoctorRepository dr;
    private final PatientRepository pr;
    private final AdminRepository ar;
    private final SuperAdminRepository sr;
    private final MedicalHistoryRecordRepository mhrr;


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }

    public IndexController(DoctorRepository dr, PatientRepository pr, AdminRepository ar, SuperAdminRepository sr, MedicalHistoryRecordRepository mhrr) {
        this.pr = pr;
        this.ar = ar;
        this.sr = sr;
        this.dr = dr;
        this.mhrr = mhrr;
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
    public String doctorRegister(Model model)
    {
        model.addAttribute("doctor", new Doctor());
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

    @RequestMapping("/pendingDoctors")
    public String pendingDoctors(Model model)
    {
        model.addAttribute("doctor", dr.findByStatus(Status.pending));
        model.addAttribute("selected", new Doctor());
        model.addAttribute("approved", Status.approved);
        model.addAttribute("rejected", Status.rejected);
        return "superadmin/pendingDoctors";
    }

    @RequestMapping("/verifyPassword-sa")
    public String verifyPasswordSA(Model model)
    {
       model.addAttribute("doctor", new Doctor());
       return "superadmin/verifyPassword";
    }

    @RequestMapping("/patient-personal-info")
    public String personalInfo(Model model)
    {
        Patient p = pr.findByUsername(Visitor.currentUser);
        model.addAttribute("patient", p);
        model.addAttribute("records", mhrr.findByPatient(p));
        return "patient/info";
    }

    @RequestMapping("/patient-add-history")
    public String addHistory(Model model)
    {
        model.addAttribute("medicalhistoryrecord", new MedicalHistoryRecord());
        return "patient/uploadHistory";
    }

    @RequestMapping("/p-search-doctor")
    public String searchDoctor(Model model)
    {
        model.addAttribute("doctors", dr.findAll());
        return "patient/doctors";
    }

    @RequestMapping("/addReged")
    public String Reged(Model model)
    {
        model.addAttribute("educationrecord", new EducationRecord());
        return "doctor/addHistory";
    }
}


