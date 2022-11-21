package tele.doc.project.domain;

import tele.doc.project.systems.others.MedicalHistoryUploader;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
public class Patient extends Person{
    private Date DOB;
    private long age;
    @OneToMany
    @JoinColumn(name = "patient_id")
    private Set<MedicalHistoryRecord> medicalHistoryRecords = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "patient_id")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "patient_id")
    private Set<Appointment> appointments = new HashSet<>();

    public long getAge()
    {
        Date date = new Date();
        long AgeInMS = date.getTime() - DOB.getTime();
        age = AgeInMS;
        return AgeInMS/(86400000L*365);
    }


    public Patient()
    {}

    public Patient(String name, String email, String username, String password, String address, Date DOB) {
        super(name, email, username, password, address);
        this.DOB = DOB;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Set<MedicalHistoryRecord> getMedicalHistoryRecords() {
        return medicalHistoryRecords;
    }

    public void setMedicalHistoryRecords(Set<MedicalHistoryRecord> medicalHistoryRecords) {
        this.medicalHistoryRecords = medicalHistoryRecords;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Patient{" +
                ", DOB=" + DOB +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Set<Doctor> searchDoctor(String s)
    {
        return new HashSet<>();
    }

    public boolean payForAppointment(Appointment a)
    {
        return true;
    }

    public void bookAppointment(Doctor d, Date date)
    {

    }

    public Prescription viewPresciption(Appointment a)
    {
        return new Prescription();
    }

    public void printPrescription(Prescription p)
    {

    }

    public void renewPrescription(Appointment a)
    {

    }

    public boolean uploadMedicalHistory()
    {
        return true;
    }

    public void manageAppointments()
    {

    }


}
