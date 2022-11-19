package tele.doc.project.systems.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Status;
import tele.doc.project.domain.SuperAdmin;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.SuperAdminRepository;

@Service
public class AdmitDoctorSystem extends SuperAdminVerification{
    @Autowired private JavaMailSender javaMailSender;
    private final DoctorRepository dr;
    String username;
    public AdmitDoctorSystem(SuperAdminRepository sr, DoctorRepository dr)
    {
        super(sr);
        this.dr = dr;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


    public void sendEmail(String email, Status type)
    {
        Doctor sa = dr.findByUsername(username);
        System.out.println(sa.getEmail());
        System.out.println(username);
        System.out.println(email);
        try
        {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(email);
            mailMessage.setTo(sa.getEmail());
            if (type == Status.approved)
            {
                mailMessage.setText("We are happy to inform you that you have been accepted at TeleDoc. You can now log in to your account");
                mailMessage.setSubject("Accepted at TeleDoc!");
            }

            else
            {
                mailMessage.setText("We are sorry to inform you but you couldn't be accepted at TeleDoc. The information you provided could not be verified.");
                mailMessage.setSubject("Not Accepted at TeleDoc!");
            }

            javaMailSender.send(mailMessage);
        }

        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Error while sending mail");
        }
    }

}
