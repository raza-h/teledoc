package tele.doc.project.systems.others;

import tele.doc.project.domain.Appointment;
import tele.doc.project.domain.Prescription;
import tele.doc.project.domain.Status;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.AppointmentRepository;
import tele.doc.project.repositories.PrescriptionRepository;

import javax.net.ssl.SSLServerSocket;
import java.util.Optional;

public class PrescriptionCreationSystem extends DoctorVerification{
    private String username;
    private String password;
    private String medication;
    private Long id;
    private String diagnosis;

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    AppointmentRepository ar;
    PrescriptionRepository pr;

    public PrescriptionCreationSystem(AppointmentRepository ar, PrescriptionRepository pr)
    {
        this.ar = ar;
        this.pr = pr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean checkAttr()
    {
        if (diagnosis.equals("") || medication.equals("") || password.equals(""))
        {
            Visitor.errorMessage="Field left empty";
            return false;
        }

        if(!verifyUser(username,password))
        {
            Visitor.errorMessage="Incorrect Password";
            return false;
        }

        Optional<Appointment> a=ar.findById(id);
        Appointment app=a.get();
        Prescription p= new Prescription(diagnosis,medication,app);
        app.setStatus(Status.removed);
        pr.save(p);
        app.setPrescription(p);
        ar.save(app);
        return true;
    }
}
