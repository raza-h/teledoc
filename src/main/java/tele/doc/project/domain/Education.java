package tele.doc.project.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

public class Education {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private Long id;
    private Date startDate;
    private Date endDate;
    private String programme;
    private String institute;

    public Education(){}

    public Education(Date startDate, Date endDate, String programme, String institute) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.programme = programme;
        this.institute = institute;
    }

    /*public Long getId() {
        return id;
    }*/
    /*public void setId(Long id) {
        this.id = id;
    }*/

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
    public String toString() {
        return "Education{" +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", programme='" + programme + '\'' +
                ", institute='" + institute + '\'' +
                '}';
    }
}
