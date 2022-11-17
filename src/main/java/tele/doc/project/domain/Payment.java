package tele.doc.project.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String ccNum;
    String CVV;
    String name;
    Date expiryDate;

    @OneToOne(mappedBy = "paymentGateway")
    private Appointment appointment;
    String address;

    public Payment() {
    }

    public Payment(String ccNum, String CVV, String name, Date expiryDate, String address) {
        this.ccNum = ccNum;
        this.CVV = CVV;
        this.name = name;
        this.expiryDate = expiryDate;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", ccNum='" + ccNum + '\'' +
                ", CVV='" + CVV + '\'' +
                ", name='" + name + '\'' +
                ", expiryDate=" + expiryDate +
                ", address='" + address + '\'' +
                '}';
    }
}
