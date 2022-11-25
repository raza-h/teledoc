package tele.doc.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.AppointmentRepository;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.PrescriptionRepository;
import tele.doc.project.systems.others.DecideAppointmentSystem;
import tele.doc.project.systems.others.PrescriptionCreationSystem;
import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

@Controller
public class DoctorController {
    private final DoctorRepository dr;
    private final AppointmentRepository ar;
    private final PrescriptionRepository pr;
    long id;
    PrescriptionCreationSystem pcs;
    Status status;
    DecideAppointmentSystem das;

    DoctorController(DoctorRepository dr, AppointmentRepository ar, PrescriptionRepository pr)
    {
        this.dr = dr; this.ar=ar; this.pr=pr;
    }
    @PostMapping("/doctorAppointments")
    public String viewAppointments(@ModelAttribute("selected") Appointment a)
    {
        das = new DecideAppointmentSystem(ar);
        status=a.getStatus();

        id = a.getId();
        if(status.equals(Status.rejected))
        {

            Optional<Appointment> a1 = ar.findById(id);
            Appointment app = a1.get();
            app.setStatus(Status.rejected);
            das.save(app);
            return "redirect:/doctorAppointments";
        }
        else {

            return "redirect:/appointmentDetails";
        }
    }
    @PostMapping("/appointmentDetails")
    public String appointmentInfo ( @RequestParam("link")String link, @RequestParam("amount") String amount) throws IOException, ParseException
    {
        Optional<Appointment> a1 = ar.findById(id);
        Doctor d = dr.findByUsername(Visitor.currentUser);
        if(   link.equals("") || amount.equals("") ) {
            Visitor.errorMessage="Field left empty";
            return "redirect:/problem";
        }
        else {
            Appointment app = a1.get();
            float result = Float.parseFloat(amount);
            app.setAmount(result);
            app.setLink(link);
            app.setStatus(Status.approved);
            das.save(app);
            return "redirect:/doctorAppointments";

        }
    }

    @PostMapping ("/addPrescription")
    public String addPrescription(@ModelAttribute("selected") Appointment a)
    {
        pcs = new PrescriptionCreationSystem(ar, pr);
        pcs.setId(a.getId());
        return "redirect:/prescriptionDetails";
    }
    @PostMapping("/prescriptionDetails")
    public String PrescriptionDetails(@RequestParam ("medication") String m, @RequestParam("diagnosis") String d, @RequestParam("password") String pres)
    {
        pcs.setPassword(pres);
        pcs.setMedication(m);
        pcs.setUsername(Visitor.currentUser);
        pcs.setDiagnosis(d);
        if(!pcs.checkAttr())
        {
            return "redirect:/problem";
        }
        return "redirect:/addPrescription";
    }

}