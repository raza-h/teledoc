package tele.doc.project.systems.regloc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.PatientRepository;
import tele.doc.project.repositories.EducationRecordRepository;
import tele.doc.project.repositories.MedicalHistoryRecordRepository;

import javax.swing.text.ElementIterator;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PatientRegister implements RegisterSystem{
    String name;
    String username;
    String email;
    String address;
    String password;

    private String date;

    @Autowired
    PatientRepository pr;
    public int count = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String Date)
    {
        this.date = Date;
    }

    PatientRegister(){count = 0;}

    public boolean checkAttributes()
    {
        Patient p = pr.findByEmail(email);
        Patient p2 = pr.findByUsername(username);

        if (name.equals("") || username.equals("") || email.equals("") || password.equals("") || address.equals("") )
        {
            Visitor.errorMessage = "Field Left Empty";
            return false;
        }

        if (p != null)
        {
            Visitor.errorMessage= "Email already exists";
            return false;
        }

        if (p2 != null)
        {
            Visitor.errorMessage = "Username already exists";
            return false;
        }

        if (password.length() < 8)
        {
            Visitor.errorMessage = "Password must be atleast 8 characters.";
            return false;
        }

        return true;
    }


    /*public boolean checkAttributes2(String date1, String date2, MultipartFile file, String institute, String programme)
    {
        if (date1.equals("") || date2.equals("") || file.isEmpty() || institute.equals("") || programme.equals(""))
        {
            Visitor.errorMessage="Field Left empty";
            return false;
        }

        return true;
    }

    public boolean formAttr(String date1, String date2, MultipartFile file, String institute, String programme, String select) throws ParseException, IOException {
        if (!checkAttributes2(date1,date2, file, institute, programme))
        {
            Visitor.errorMessage="Field Left Empty";
            return false;
        }
        if (count == 0)
        {
            Doctor d = new Doctor(name, email, username, password, address, qualification,specialization,0, Status.pending);
            d.setIBAN(IBAN);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);
            byte[] arr = file.getBytes();
            EducationRecord er = new EducationRecord(d1,d2,institute, programme);
            er.setFile(arr);
            er.setDoctor(d);
            count++;
            System.out.println(er.toString());
            System.out.println(d.toString());
            dr.save(d);
            mhrr.save(er);
        }

        else
        {
            Doctor d = dr.findByUsername(username);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);
            byte[] arr = file.getBytes();
            EducationRecord er = new EducationRecord(d1,d2,institute, programme);
            er.setFile(arr);
            er.setDoctor(d);
            System.out.println(er.toString());
            System.out.println(d.toString());
            mhrr.save(er);
        }

        if(select.equals("finished"))
        {
            count = 0;
        }

        return true;
    }*/

    public boolean register() throws ParseException {
        if(!checkAttributes() || date.equals(""))
        {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = format.parse(date);
        Patient p = new Patient();
        p.setDOB(d1);
        p.setUsername(username);
        p.setAddress(address);
        p.setPassword(password);
        p.setEmail(email);
        p.setName(name);
        pr.save(p);
        return true;
    }
}
