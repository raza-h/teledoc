package tele.doc.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.EducationRecord;
import tele.doc.project.domain.Patient;
import tele.doc.project.domain.Person;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.systems.regloc.DoctorRegister;
import tele.doc.project.systems.regloc.PatientRegister;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;


@Controller
public class RegisterController {
    private final DoctorRepository dr;
    @Autowired DoctorRegister dreg;
    @Autowired PatientRegister preg;

    RegisterController(DoctorRepository dr)
    {
        this.dr = dr;
    }

    @PostMapping("/doctor-register")
    public String registerDoc(@ModelAttribute("doctor") Doctor d)
    {
        dreg.setName(d.getName());
        dreg.setUsername(d.getUsername());
        dreg.setEmail(d.getEmail());
        dreg.setSpecialization(d.getSpecialization());
        dreg.setAddress(d.getAddress());
        dreg.setPassword(d.getPassword());
        dreg.setQualification(d.getQualification());
        dreg.setIBAN(d.getIBAN());
        if(!dreg.register())
        {
            return "redirect:/problem";
        }
        return "redirect:/addReged";
    }

    @PostMapping("/addReged")
    public String reged(@ModelAttribute("educationrecord") EducationRecord ed, @RequestParam("date1") String startDate, @RequestParam("date2") String endDate, @RequestParam("select") String select, @RequestParam("filer")MultipartFile file) throws ParseException, IOException {
        System.out.println(ed.getInstitute());
        System.out.println(ed.getProgramme());
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(file.getName());
        System.out.println(select);
        System.out.println(dreg.getUsername());
        if (select.equals("addmore"))
        {
            if (!dreg.formAttr(startDate,endDate,file,ed.getInstitute(), ed.getProgramme(), select))
            {
                return "redirect:/problem";
            }

            return "redirect:/addReged";
        }

        if (!dreg.formAttr(startDate,endDate,file,ed.getInstitute(),ed.getProgramme(), select))
        {
            return "redirect:/problem";
        }
        return "redirect:/";
    }

    @PostMapping("/patient-register")
    public String registerPat(@ModelAttribute("patient") Patient p, @RequestParam("Date") String Date) throws ParseException {
        preg.setName(p.getName());
        preg.setUsername(p.getUsername());
        preg.setEmail(p.getEmail());
        preg.setAddress(p.getAddress());
        preg.setPassword(p.getPassword());
        preg.setDate(Date);
        if(!preg.register())
        {
            return "redirect:/problem";
        }
        return "redirect:/patient-login";
    }

    /*
    private final DoctorRepository dr;

    RegisterController(DoctorRepository dr)
    {
        this.dr= dr;
    }

    @PostMapping("/doctor-register")
    public String RegDoc(@RequestBody String data)
    {
        String[] parsedData = {"username", "email", "phone", "address", "password"};
        System.out.println(data);
        String[] keyValuePairs = data.split("&");
        for (int i = 0; i < keyValuePairs.length; i++)
        {
            String[] keyValue = keyValuePairs[i].split("=", 2);
            String key = keyValue[0];
            String value = keyValue[1].length() > 1 ? keyValue[1]:"";
            key = URLDecoder.decode(key, StandardCharsets.UTF_8);
            value = URLDecoder.decode(value, StandardCharsets.UTF_8);
            parsedData[i] = value;
            System.out.println(value + '\n');
        }

        Doctor d = new Doctor();
        d.setUsername(parsedData[0]);
        d.setEmail(parsedData[1]);
        d.setAddress(parsedData[3]);
        d.setPassword(parsedData[4]);
        dr.save(d);

        return "redirect:/doctor-login";
    }
 */


    //@PostMapping(value = "/doctor-login", consumes = {"*/*"})
    /*public String LogDoc(@RequestBody String data)
    {
        String[] parsedData = {"username", "password"};
        String[] keyValuePairs = data.split("&");
        for (int i = 0; i < keyValuePairs.length; i++)
        {
            String[] keyValue = keyValuePairs[i].split("=", 2);
            String key = keyValue[0];
            String value = keyValue[1].length() > 1 ? keyValue[1]:"";
            key = URLDecoder.decode(key, StandardCharsets.UTF_8);
            value = URLDecoder.decode(value, StandardCharsets.UTF_8);
            parsedData[i] = value;
            System.out.println(value + '\n');
        }

        Doctor registered = dr.findByUsername(parsedData[0]);
        if (registered != null && registered.getPassword().equals(parsedData[1]))
        {
            String username = registered.getUsername();
            return "redirect:/doctor?username=" + username;
        }

        return "redirect:/doctor-login";
    }

     */
}


