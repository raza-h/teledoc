package tele.doc.project.domain;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Doctor extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String qualification;
    private String Specialization;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Education education;

    private float rating;
    private boolean status;

    @OneToMany
    @JoinColumn(name = "doctor_id")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "doctor_id")
    private Set<Appointment> appointments = new HashSet<>();

    public Doctor() {
        status = false;
    }

    public Doctor(String qualification, String specialization, float rating, boolean status) {
        this.qualification = qualification;
        Specialization = specialization;
        this.rating = rating;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
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
}
