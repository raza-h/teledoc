package tele.doc.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.*;
import tele.doc.project.systems.others.SearchSystem;

import javax.print.Doc;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
public class IndexController {

    private final DoctorRepository dr;
    private final PatientRepository pr;
    private final AdminRepository ar;
    private final SuperAdminRepository sr;
    private final MedicalHistoryRecordRepository mhrr;
    private final AppointmentRepository apr;

    @Autowired
    SearchSystem ss;


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
    }

    public IndexController(DoctorRepository dr, PatientRepository pr, AdminRepository ar, SuperAdminRepository sr, MedicalHistoryRecordRepository mhrr, AppointmentRepository apr) {
        this.pr = pr;
        this.ar = ar;
        this.sr = sr;
        this.dr = dr;
        this.mhrr = mhrr;
        this.apr = apr;
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
    public String patientRegister(Model model)
    {
        model.addAttribute("patient", new Patient());
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
    public String searchDoctor(Model model, @RequestParam(value="select",required=false) String option, @RequestParam(value="specializationList", required = false) String specialization, @RequestParam(value="searchQuery",required = false) String name)
    {
        Set<String> arr = dr.findAllBySpecialization();
        if (option == null)
        {
            Iterator<String> it = arr.iterator();

            for(;it.hasNext();)
            {
                System.out.println(it.next());
            }

            model.addAttribute("specializations", arr);
            model.addAttribute("doctors", dr.findByStatus(Status.approved));
        }

        else if (option.equals("name"))
        {
            Set<Doctor> allDocs = ss.filterDoctorList(name);
            Iterator<Doctor> it = allDocs.iterator();

            for(;it.hasNext();)
            {
                System.out.println(it.next().getName());
            }
            model.addAttribute("specializations", arr);
            model.addAttribute("doctors", allDocs);
        }

        else if(option.equals("specialization"))
        {
            Set<Doctor> allDocs = dr.findByStatus(Status.approved);
            Iterator<Doctor> it = allDocs.iterator();
            Set<Doctor> approved = new HashSet<>();
            while(it.hasNext())
            {
                Doctor d = it.next();
                if(d.getSpecialization().equals(specialization))
                {
                    approved.add(d);
                }
            }

            model.addAttribute("specializations", arr);
            model.addAttribute("doctors", approved);
        }

        return "patient/doctors";
    }

    @RequestMapping("/addReged")
    public String Reged(Model model)
    {
        model.addAttribute("educationrecord", new EducationRecord());
        return "doctor/addHistory";
    }

    @RequestMapping("/approvedDoctors")
    public String removeDoctor(Model model)
    {
        Set<Doctor> docs = dr.findByStatus(Status.approved);
        model.addAttribute("doctor", docs);
        model.addAttribute("selected", new Doctor());
        return "superadmin/approvedDoctors";
    }

    @RequestMapping("/verifyPassword-sa-2")
    public String verifyPasswordSA2(Model model)
    {
        model.addAttribute("doctor", new Doctor());
        return "superadmin/verifyPassword";
    }

    @RequestMapping("/doctor-list-p/{username}")
    public String viewDoctor(@PathVariable("username") String username, Model model)
    {
        System.out.println(username);
        Set<Appointment> a = apr.findByDoctor(dr.findByUsername(username));
        Set<Appointment> filtered = new HashSet<>();
        Iterator<Appointment> fit = a.iterator();
        while(fit.hasNext())
        {
            Appointment ap = fit.next();
            if (ap.getStatus()== Status.approved || ap.getStatus()==Status.pending)
            {
                filtered.add(ap);
            }
        }

        model.addAttribute("appointments", filtered);
        model.addAttribute("doctor", dr.findByUsername(username));
        return "patient/doctor-info";
    }

    @RequestMapping("/bookAppointment")
    public String bookingAppointment(Model model)
    {
        model.addAttribute("appt", new Appointment());
        return "patient/bookAppointment";
    }

    @RequestMapping("/patient-appointments")
    public String manageAppointments(Model model)
    {
        Set<Appointment> a = apr.findByPatient(pr.findByUsername(Visitor.currentUser));
        Set<Appointment> ap = new HashSet<>();
        Iterator<Appointment> it = a.iterator();
        while(it.hasNext())
        {
            Appointment app = it.next();
            if (app.getStatus() != Status.rejected)
            {
                ap.add(app);
            }
        }

        model.addAttribute("appointments", ap);
        model.addAttribute("appoint", new Appointment());
        return "patient/appointments";
    }

}