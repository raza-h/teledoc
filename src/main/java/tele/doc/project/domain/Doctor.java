package tele.doc.project.domain;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Doctor extends Person{
    private String qualification;

    public Set<EducationRecord> getEducation() {
        return education;
    }

    public void setEducation(Set<EducationRecord> education) {
        this.education = education;
    }

    private String Specialization;

    @OneToMany
    @JoinColumn(name = "doctor_id")
    private Set<EducationRecord> education = new HashSet<>();

    private float rating;
    private Status status;

    @OneToMany
    @JoinColumn(name = "doctor_id")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "doctor_id")
    private Set<Appointment> appointments = new HashSet<>();

    public Doctor() {
        status = Status.pending;
    }

    public Doctor(String name, String email, String username, String password, String address, String qualification, String specialization, float rating, Status status) {
        super(name, email, username, password, address);
        this.qualification = qualification;
        Specialization = specialization;
        this.rating = rating;
        this.status = status;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Status isStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        return "Doctor{" +

                ", qualification='" + qualification + '\'' +
                ", Specialization='" + Specialization + '\'' +
                ", rating=" + rating +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public void approveAppointment(Appointment a)
    {

    }

    public void rejectAppointment(Appointment a)
    {

    }

    public void viewAppointment(Appointment a)
    {

    }

    public float calcRating()
    {
        return 0;
    }

    public boolean prescribeMedicine(Appointment a)
    {
        return true;
    }

    public void viewMedicalHistory(Appointment a)
    {

    }
}
