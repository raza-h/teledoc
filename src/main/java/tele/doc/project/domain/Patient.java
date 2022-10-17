package tele.doc.project.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
public class Patient extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date DOB;

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
        return AgeInMS/(86400000L*365);
    }


    public Patient()
    {}

    public Patient(Date DOB) {
        this.DOB = DOB;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", DOB=" + DOB +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
