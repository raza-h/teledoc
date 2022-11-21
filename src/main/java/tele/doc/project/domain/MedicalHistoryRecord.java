package tele.doc.project.domain;

import javax.persistence.*;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Entity
public class MedicalHistoryRecord
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Date dateDiagnosed;
    private String medication;
    private String duration;
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] file;

    public String getFile() {
        return Base64.getEncoder().encodeToString(file);
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @ManyToOne
    private Patient patient;

    public MedicalHistoryRecord() {
    }

    public MedicalHistoryRecord(String name, Date dateDiagnosed, String medication, String duration) {
        this.name = name;
        this.dateDiagnosed = dateDiagnosed;
        this.medication = medication;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateDiagnosed() {
        return dateDiagnosed;
    }

    public void setDateDiagnosed(Date dateDiagnosed) {
        this.dateDiagnosed = dateDiagnosed;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicalHistoryRecord that = (MedicalHistoryRecord) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MedicalHistoryRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateDiagnosed=" + dateDiagnosed +
                ", medication='" + medication + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
