package tele.doc.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Status;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.SuperAdminRepository;
import tele.doc.project.systems.others.AdmitDoctorSystem;
import tele.doc.project.systems.others.DoctorRemovalSystem;

import javax.print.Doc;
import java.util.Set;

@Controller
public class SuperDoctorController {
    SuperAdminRepository dr;
    DoctorRepository docrep;
    Status status;
    String username;
    @Autowired AdmitDoctorSystem ads;
    @Autowired
    DoctorRemovalSystem rds;

    SuperDoctorController(SuperAdminRepository dr, DoctorRepository docrep)
    {
        this.dr = dr;
        this.docrep = docrep;
    }

    @PostMapping("/pendingDoctors")
    public String pendingDoctors(@ModelAttribute("selected") Doctor d)
    {
        System.out.println(d.getUsername());
        System.out.println(d.isStatus());
        status = d.isStatus();
        username = d.getUsername();
        return "redirect:/verifyPassword-sa";
    }

    @PostMapping("/verifyPassword-sa")
    public String verifyPassword(@ModelAttribute("doctor") Doctor d)
    {
        if (ads.verifyUser(Visitor.currentUser, d.getPassword()))
        {
            ads.setUsername(username);
            ads.sendEmail("razah12145@gmail.com", status);
            Doctor doc = docrep.findByUsername(username);
            doc.setStatus(status);
            docrep.save(doc);
        }

        else
        {
            return "redirect:/problem";
        }
        return "redirect:/pendingDoctors";
    }

    @PostMapping("/approvedDoctors")
    public String removeDoctor(@ModelAttribute("selected") Doctor d)
    {
        rds.setUsername(d.getUsername());
        if (!rds.checkStatus())
        {
            return "redirect:/problem";
        }

        return "redirect:/verifyPassword-sa-2";
    }

    @PostMapping("/verifyPassword-sa-2")
    public String verifyPasswordSA2(@ModelAttribute("doctor") Doctor d)
    {
        if (!rds.changeStatus(d.getPassword()))
        {
            return "redirect:/problem";
        }

        return "redirect:/approvedDoctors";
    }


}
