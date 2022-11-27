package tele.doc.project.controllers;

import org.hibernate.boot.model.source.internal.hbm.EmbeddableSourceVirtualImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.*;
import tele.doc.project.systems.others.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.print.Doc;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Controller
public class PatientController {
    private final PatientRepository pr;
    private final DoctorRepository dr;
    private  final AppointmentRepository ar;
    private final ReviewRepository rr;
    private final MedicalHistoryRecordRepository mhrr;
    RenewPrescriptionSystem rps;
    @Autowired
    private JavaMailSender javaMailSender;
    BookAppointmentSystem bas;
    MedicalHistoryUploader mhu;
    CancelAppointmentSystem cas;
    PaymentSystem ps;
    RatingSystem prs;
    Review r;
    PatientController(PatientRepository pr, MedicalHistoryRecordRepository mhrr, DoctorRepository dr, AppointmentRepository ar, ReviewRepository rr)
    {
        this.pr = pr;
        this.mhrr = mhrr;
        this.dr = dr;
        this.ar = ar;
        this.rr = rr;
    }


    @PostMapping(value = "/patient-add-history", consumes = {"*/*"})
    public String addHistory(@ModelAttribute("medicalhistoryrecord") MedicalHistoryRecord mhr, @RequestParam("date") String date, @RequestParam("filer") MultipartFile f) throws IOException, ParseException {
        mhu = new MedicalHistoryUploader(pr, mhrr);
        if(!mhu.upload(date, f, mhr))
        {
            return "redirect:/problem";
        }
        return "patient/uploadHistory";
    }

    @PostMapping("/doctor-list-p/{username}")
    public String bookAppointment(@ModelAttribute("doctor") Doctor d, @PathVariable("username") String username, @RequestParam("doctor-func") String chosen)
    {
        if (chosen.equals("appt"))
        {
            bas = new BookAppointmentSystem(ar);
            bas.setUsername(d.getUsername());
            return "redirect:/bookAppointment";
        }

        else if (chosen.equals("rate"))
        {
            prs = new RatingSystem(rr,dr,pr,ar);
            prs.setUsername(d.getUsername());
            return "redirect:/provideRating";
        }

        return "redirect:/doctor-list-p/" + username;
    }

    @PostMapping("/bookAppointment")
    public String bookedAppointment(@RequestParam("date") String date, @RequestParam("time") String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
        Date d = format.parse(date);
        Date t = format2.parse(time);
        Date l = new Date();
        l.setTime(d.getTime()+t.getTime() + 18000000);
        if (!bas.checkDate(dr.findByUsername(bas.getUsername()), l))
        {
            return "redirect:/problem";
        }

        Time timer = new Time(l.getTime() + 1800000);
        Appointment a = new Appointment();
        Patient p = pr.findByUsername(Visitor.currentUser);
        a.setDate(l);
        a.setTime(timer);
        System.out.println(a.getTime());
        a.setDoctor(dr.findByUsername(bas.getUsername()));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("razah12145@gmail.com");
        mailMessage.setTo(dr.findByUsername(bas.getUsername()).getEmail());
        mailMessage.setText("You have a pending appointment with " + p.getName() + " on " + a.getDate() + " - " + a.getTime());
        mailMessage.setSubject("New Appointment!");
        javaMailSender.send(mailMessage);
        a.setPatient(p);
        a.setStatus(Status.pending);
        ar.save(a);
        return "redirect:/doctor-list-p/" + bas.getUsername();
    }

    @PostMapping("/patient-appointments")
    public String cancelAppointment(@ModelAttribute("appoint") Appointment a) throws ChangeSetPersister.NotFoundException {
        System.out.println(a.getId());
        Optional<Appointment> ap = ar.findById(a.getId());
        Appointment app = ap.orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        Appointment appo = ap.get();
        if (appo.getStatus() != Status.removed)
        {
            System.out.println(appo.getDate());
            cas = new CancelAppointmentSystem(ar);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("razah12145@gmail.com");
            mailMessage.setTo(appo.getDoctor().getEmail());
            mailMessage.setSubject("Appointment canceled");
            mailMessage.setText("Your appointment with " + appo.getPatient().getName() + " at " + appo.getDate() + " - " + appo.getTime() + " has been canceled.");
            javaMailSender.send(mailMessage);
            cas.cancelAppointment(appo);
        }

        return "redirect:/patient-appointments";
    }

    @PostMapping("/renewPrescription")
    public String renewPres(@ModelAttribute("appoint") Appointment a)  {
        Optional<Appointment> a1=ar.findById(a.getId());
        Appointment a2=a1.get();
        Doctor d= a2.getDoctor();
        rps=new RenewPrescriptionSystem(ar);
        rps.setUsername(d.getUsername());
        return "redirect:/renewAppointment";
    }
    @PostMapping("/renewAppointment")
    public String renewAppointment(@RequestParam("date") String date, @RequestParam("time") String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
        Date d = format.parse(date);
        Date t = format2.parse(time);
        Date l = new Date();
        l.setTime(d.getTime()+t.getTime() + 18000000);
        if (!rps.checkDate(dr.findByUsername(rps.getUsername()), l))
        {
            return "redirect:/problem";
        }
        Time timer = new Time(l.getTime() + 1800000);
        Appointment a = new Appointment();
        Patient p = pr.findByUsername(Visitor.currentUser);
        a.setDate(l);
        a.setTime(timer);
        System.out.println(a.getTime());
        a.setDoctor(dr.findByUsername(rps.getUsername()));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("razah12145@gmail.com");
        mailMessage.setTo(dr.findByUsername(rps.getUsername()).getEmail());
        mailMessage.setText("You have a pending appointment with " + p.getName() + " on " + a.getDate() + " - " + a.getTime());
        mailMessage.setSubject("New Appointment!");
        javaMailSender.send(mailMessage);
        a.setPatient(p);
        a.setStatus(Status.pending);
        ar.save(a);
        return "redirect:/patient";
    }

    @PostMapping("/p-appointment/{id}")
    public String payForAppointment(@ModelAttribute("appointment") Appointment a, @PathVariable("id") Long id)
    {
        ps = new PaymentSystem();
        Optional<Appointment> ap= ar.findById(id);
        ps.setA(ap.get());
        if (ps.getA().isPaid() || ps.getA().getStatus() != Status.approved)
        {
            Visitor.errorMessage = "Already paid or not approved";
            return "redirect:/problem";
        }
        return "redirect:/payment";
    }

    @RequestMapping("/payment")
    public String paymentCred(Model model)
    {
        model.addAttribute("appointment", ps.getA());
        model.addAttribute("patient", ps.getA().getPatient());
        model.addAttribute("doctor", ps.getA().getDoctor());
        model.addAttribute("payment", new Payment());
        return "patient/payment";
    }

    @PostMapping("/payment")
    public String getCred(@ModelAttribute("payment") Payment p, @RequestParam("month") String month, @RequestParam("year") String year)
    {
        System.out.println(p.getName());
        System.out.println(p.getCcNum());
        System.out.println(p.getCVV());
        if (month.equals("") || year.equals(""))
        {
            Visitor.errorMessage= "Field Left Empty";
            return "redirect:/problem";
        }
        Date d = new Date();
        int monthy = Integer.parseInt(month);
        int yeary = Integer.parseInt(year);
        d.setMonth(monthy);
        d.setYear(yeary);
        ps.setName(p.getName());
        ps.setAddress(p.getAddress());
        ps.setCcNum(p.getCcNum());
        ps.setCVV(p.getCVV());
        ps.setExpiryDate(d);
        if(!ps.verifyCredentials())
        {
            return "redirect:/problem";
        }

        ps.getA().setPaid(true);
        ar.save(ps.getA());
        return "redirect:/patient-appointments";
    }

    @PostMapping("/provideRating")
    public String providedRating(@RequestParam("rate") String rating, @RequestParam("review") String review) throws ParseException {
        if (!prs.checkAppointment()) {
            Visitor.errorMessage = "Rating not allowed without completed appointment(s)";
            return "redirect:/problem";
        }
        float ratingInt = Integer.parseInt(rating);
        r = new Review();
        Patient p = pr.findByUsername(Visitor.currentUser);
        Doctor doc = dr.findByUsername(prs.getUsername());
        Date date = new Date();
        r.setDate(date);
        r.setRating(ratingInt);
        r.setReview(review);
        r.setDoctor(dr.findByUsername(prs.getUsername()));
        r.setPatient(p);
        return "redirect:/patient-verification";
    }

    @PostMapping("/patient-verification")
    public String verifiedRating(@RequestParam("username") String username, @RequestParam("password") String password) throws ParseException {
        Patient p = pr.findByUsername(Visitor.currentUser);
        if((username.equals(p.getUsername())) && (password.equals(p.getPassword())))
        {
            prs.enterRating(r);
            //r=null;
            return "redirect:/patient";
        }
        Visitor.errorMessage="Wrong password";
        return "redirect:/problem";
    }

}

