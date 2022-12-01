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
import tele.doc.project.systems.others.MedicalHistoryUploader;
import tele.doc.project.systems.others.SearchSystem;

import javax.print.Doc;
import java.awt.*;
import java.util.*;

@Controller
public class IndexController {

    private final DoctorRepository dr;
    private final PatientRepository pr;
    private final AdminRepository ar;
    private final SuperAdminRepository sr;
    private final MedicalHistoryRecordRepository mhrr;
    private final AppointmentRepository apr;
    private final PrescriptionRepository prr;

    private final EducationRecordRepository edr;
    private final ReviewRepository rr;

    @Autowired
    SearchSystem ss;

    MedicalHistoryUploader mhu;


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5000000);
        return multipartResolver;
    }

    public IndexController(DoctorRepository dr, PatientRepository pr, AdminRepository ar, SuperAdminRepository sr, MedicalHistoryRecordRepository mhrr, AppointmentRepository apr, PrescriptionRepository prr, EducationRecordRepository edr, ReviewRepository rr) {
        this.pr = pr;
        this.ar = ar;
        this.sr = sr;
        this.dr = dr;
        this.mhrr = mhrr;
        this.apr = apr;
        this.prr = prr;
        this.edr = edr;
        this.rr = rr;
    }

    @RequestMapping("/")
    public String index()
    {
        Date date = new Date();
        Visitor.currentUser="";
        Visitor.userType="";
        Iterable<SuperAdmin> sas = sr.findAll();
        Iterator<SuperAdmin> it = sas.iterator();
        Set<SuperAdmin> sa = new HashSet<>();

        Iterable<Admin> as = ar.findAll();
        Iterator<Admin> it2 = as.iterator();
        Set<Admin> aa = new HashSet<>();

        while (it.hasNext())
        {
            sa.add(it.next());
        }

        while(it2.hasNext())
        {
            aa.add(it2.next());
        }

        if (sa.size()==0)
        {
            SuperAdmin s = new SuperAdmin();
            s.setUsername("user");
            s.setPassword("abcd1234");
            sr.save(s);
        }

        if (aa.size() == 0)
        {
            Admin a = new Admin();
            a.setUsername("user");
            a.setPassword("abcd1234");
            ar.save(a);
        }

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
        Iterable<Patient> p = pr.findAll();
        Set<Patient> ps = new HashSet<>();
        Iterator<Patient> it = p.iterator();
        while(it.hasNext())
        {
            ps.add(it.next());
        }
        model.addAttribute("patients", ps);
        return "admin/home";
    }

    @RequestMapping("/patient-list-a/{username}")
    public String pReviews(@PathVariable("username") String u, Model model)
    {
        model.addAttribute("patient", pr.findByUsername(u));
        model.addAttribute("reviews", rr.findByPatient(pr.findByUsername(u)));
        return "admin/patient-info";
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

    @RequestMapping("/doctor-personal-info")
    public String personalInfo_d(Model model)
    {
        Doctor d = dr.findByUsername(Visitor.currentUser);
        model.addAttribute("doctor", d);
        model.addAttribute("records", edr.findByDoctor(d));
        model.addAttribute("reviews", rr.findByDoctor(d));
        model.addAttribute("appointments", apr.findByDoctorAndStatus(d, Status.approved));
        return "doctor/info";
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
        model.addAttribute("reviews", rr.findByDoctor(dr.findByUsername(username)));
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

    @RequestMapping("/doctorAppointments")
    public String doctorAppointments(Model model)
    {
        Doctor d = dr.findByUsername(Visitor.currentUser);
        model.addAttribute("appointments", apr.findByDoctorAndStatus(d,Status.pending));
        model.addAttribute("selected", new Appointment());
        model.addAttribute("approved", Status.approved);
        model.addAttribute("rejected", Status.rejected);
        return "doctor/doctorAppointments";
    }
    @RequestMapping("/appointmentDetails")
    public String AppDetails(Model model)
    {
        Doctor d = dr.findByUsername(Visitor.currentUser);
        model.addAttribute("appointments", apr.findByDoctorAndStatus(d,Status.pending));
        return "doctor/AppointmentDetails";
    }

    @RequestMapping("/addPrescription")
    public String addPrescription(Model model)
    {
        Doctor d = dr.findByUsername(Visitor.currentUser);
        Set<Appointment> Appointments= apr.findByDoctorAndStatus(d,Status.approved);
        //  if(Appointments.size()!=0) {
        Set<Appointment> Appointments1 = new LinkedHashSet<Appointment>();
        Iterator<Appointment> it = Appointments.iterator();
        Iterator<Appointment> it1 = Appointments.iterator();
        Date date = new Date();
        while (it.hasNext()) {
            Appointment a = it.next();
            System.out.println(a.getDate().getTime());
            System.out.println(date.getTime());
            System.out.println(a.isPaid());
            if (a.getDate().getTime() + 1800000 < date.getTime() && a.isPaid()) {
                Appointments1.add(it1.next());
            }
        }
        model.addAttribute("Appointments1", Appointments1);
        model.addAttribute("selected", new Appointment());
        return "doctor/prescriptionsList";
    }
    @RequestMapping("/prescriptionDetails")
    public String PrescriptionDetails(Model model)
    {

        //model.addAttribute("prescription", new Prescription());
        return "doctor/PrescriptionDetails";
    }

    @RequestMapping("/p-appointment/{id}")
    public String apptDetails(@PathVariable("id") Long id, Model model)
    {
        Optional<Appointment> ap = apr.findById(id);
        model.addAttribute("appointment", ap.get());
        return "patient/appointment";
    }

    @RequestMapping("/renewPrescription")
    public String RenewPrescription(Model model)
    {
        //get appointments of patient whose status is removed
        Patient p=pr.findByUsername(Visitor.currentUser);
        Set<Appointment> appointments1=apr.findByPatientAndStatus(p,Status.removed);
        model.addAttribute("appointments",appointments1);
        return "patient/RenewPrescription";
    }

    @RequestMapping("/renewAppointment")
    public String renewAppointment(Model model)
    {

        return "patient/renewAppointment";
    }

    @RequestMapping("/p-prescription/{id}")
    public String prescriptionDeets(Model model, @PathVariable("id") Long id)
    {
        Optional<Prescription> pres = prr.findById(id);
        if(pres.get() != null)
        {
            Prescription prescription = pres.get();
            model.addAttribute("prescription", prescription);
            return "patient/prescription";
        }

        Visitor.errorMessage = "Prescription doesn't exist yet";
        return "redirect:/problem";
    }

    @RequestMapping("/patient-list-d/{username}")
    public String viewMH(Model model, @PathVariable("username") String username)
    {
        mhu = new MedicalHistoryUploader(pr, mhrr);
        Patient p = mhu.getPatient(username);
        Set<MedicalHistoryRecord> mh = mhu.getMH(p);
        model.addAttribute("patient", p);
        model.addAttribute("records", mh);
        return "doctor/patient-info";
    }

    @RequestMapping("/provideRating")
    public String provideRating()
    {
        return "patient/provideRating";
    }

    @RequestMapping("/patient-verification")
    public String patientVerify()
    {
        return "patient/patient-verification";
    }

    @RequestMapping("/doctor-list-sa/{username}")
    public String docView(Model model, @PathVariable("username") String u)
    {
        Doctor d = dr.findByUsername(u);
        model.addAttribute("doctor", d);
        model.addAttribute("records", edr.findByDoctor(d));
        model.addAttribute("reviews", rr.findByDoctor(d));
        model.addAttribute("appointments", apr.findByDoctor(d));
        return "superadmin/doctor-info";
    }

    @RequestMapping("/statistics")
    public String statistics(Model model)
    {
        Set<Doctor> it = dr.sortedByRating();

        int count1 = it.size();

        Iterable<Patient> it2 = pr.findAll();
        int count2 = 0;

        for (Patient p : it2)
        {
            count2++;
        }

        model.addAttribute("numD", count1);
        model.addAttribute("numP", count2);
        model.addAttribute("doctors", dr.sortedByRating());
        return "superadmin/statistics";
    }

    @RequestMapping("/approvedAppointments")
    public String approvedAppointments_d(Model model)
    {
        Doctor d = dr.findByUsername(Visitor.currentUser);
        Set<Appointment> aps =  apr.findByDoctor(d);
        Iterator<Appointment> it = aps.iterator();
        Set<Appointment> selected = new HashSet<>();

        while(it.hasNext())
        {
            Appointment a = it.next();
            if(a.getStatus() == Status.approved || a.getStatus() == Status.removed)
            {
                selected.add(a);
            }
        }

        model.addAttribute("appointments", selected);
        return "doctor/approvedAppointments";
    }

    @RequestMapping("/d-appointment/{id}")
    public String appointmentDetails_d(Model model, @PathVariable("id") Long id)
    {
        Optional<Appointment> p = apr.findById(id);
        model.addAttribute("appointment", p.get());
        model.addAttribute("approved", Status.approved);
        return "doctor/appointment";
    }

    @RequestMapping("/charge-home")
    public String chargeHome()
    {
        return "redirect:/patient";
    }

}