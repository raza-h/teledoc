package tele.doc.project.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

import static tele.doc.project.domain.Status.approved;

@Entity
public class SuperAdmin extends Person{
    private String specialization;
    private String experience;

    public SuperAdmin() {
    }

    public SuperAdmin(String name, String email, String username, String password, String address, String specialization, String experience) {
        super(name, email, username, password, address);
        this.specialization = specialization;
        this.experience = experience;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "SuperAdmin{" +
                ", specialization='" + specialization + '\'' +
                ", experience='" + experience + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class Education {
        public String institute;
        public Date startDate;
        public Date endDate;
        public String programme;
    }

    public boolean removeDoctor(Doctor d)
    {
        return false;
    }

    public Status verifyDoctor(Doctor d)
    {

        return approved;
    }
}
