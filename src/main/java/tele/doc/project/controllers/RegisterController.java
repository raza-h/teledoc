package tele.doc.project.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tele.doc.project.domain.Doctor;
import tele.doc.project.repositories.DoctorRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class RegisterController {

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
}
