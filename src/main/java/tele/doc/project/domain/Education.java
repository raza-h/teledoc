package tele.doc.project.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    private String programme;
    private String institute;

    @OneToOne
    private Doctor doctor;

    public Education(){}

    public Education(Date startDate, Date endDate, String programme, String institute) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.programme = programme;
        this.institute = institute;
    }

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Education education = (Education) o;

        return Objects.equals(id, education.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", programme='" + programme + '\'' +
                ", institute='" + institute + '\'' +
                '}';
    }
}
