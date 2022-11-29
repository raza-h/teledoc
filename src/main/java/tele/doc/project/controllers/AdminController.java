package tele.doc.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tele.doc.project.domain.Patient;
import tele.doc.project.repositories.PatientRepository;

@Controller
public class AdminController {
    @Autowired JavaMailSender jm;
    private final PatientRepository pr;

    AdminController(PatientRepository pr)
    {
        this.pr = pr;
    }

    @PostMapping("/admin")
    public String removePatient(@RequestParam("u") String u)
    {
        Patient p = pr.findByUsername(u);
        System.out.println("Hello");
        SimpleMailMessage mm = new SimpleMailMessage();
        mm.setFrom("razah12145@gmail.com");
        mm.setTo(p.getEmail());
        mm.setSubject("You are removed.");
        mm.setText("You have been removed from TeleDoc due to the use of foul language");
        jm.send(mm);
        pr.delete(p);
        return "redirect:/admin";
    }

}
