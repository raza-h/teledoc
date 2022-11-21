package tele.doc.project.domain;


import javax.persistence.*;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Entity
public class EducationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public String toString() {
        return "EducationRecord{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", institute='" + institute + '\'' +
                ", programme='" + programme + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EducationRecord that = (EducationRecord) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    Date startDate;
    Date endDate;
    String institute;
    String programme;
    @Lob
    @Column(columnDefinition="LONGBLOB")
    byte[] file;

    @ManyToOne
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFile() {
        return Base64.getEncoder().encodeToString(file);
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

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public EducationRecord() {
    }

    public EducationRecord(Date startDate, Date endDate, String institute, String programme) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.institute = institute;
        this.programme = programme;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }


}
