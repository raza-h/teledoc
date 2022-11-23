package tele.doc.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.AppointmentRepository;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.MedicalHistoryRecordRepository;
import tele.doc.project.repositories.PatientRepository;
import tele.doc.project.systems.others.BookAppointmentSystem;
import tele.doc.project.systems.others.CancelAppointmentSystem;
import tele.doc.project.systems.others.MedicalHistoryUploader;
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
    private final MedicalHistoryRecordRepository mhrr;
    @Autowired
    private JavaMailSender javaMailSender;
    BookAppointmentSystem bas;
    MedicalHistoryUploader mhu;
    CancelAppointmentSystem cas;

    PatientController(PatientRepository pr, MedicalHistoryRecordRepository mhrr, DoctorRepository dr, AppointmentRepository ar)
    {
        this.pr = pr;
        this.mhrr = mhrr;
        this.dr = dr;
        this.ar = ar;
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
        System.out.println(appo.getDate());
        cas = new CancelAppointmentSystem(ar);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("razah12145@gmail.com");
        mailMessage.setTo(appo.getDoctor().getEmail());
        mailMessage.setSubject("Appointment canceled");
        mailMessage.setText("Your appointment with " + appo.getPatient().getName() + " at " + appo.getDate() + " - " + appo.getTime() + " has been canceled.");
        javaMailSender.send(mailMessage);
        cas.cancelAppointment(appo);
        return "redirect:/patient-appointments";
    }


}

