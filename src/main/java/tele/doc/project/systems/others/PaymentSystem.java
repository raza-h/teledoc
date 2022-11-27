package tele.doc.project.systems.others;

import tele.doc.project.domain.Appointment;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Patient;
import tele.doc.project.domain.Visitor;

import java.util.Date;

public class PaymentSystem {

    Appointment a;
    String ccNum;
    String CVV;
    String address;
    String name;
    Date expiryDate;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Appointment getA() {
        return a;
    }

    public void setA(Appointment a) {
        this.a = a;
    }

    public boolean verifyCredentials()
    {
        if (ccNum.equals("") || address.equals("") || CVV.equals("") || name.equals(""))
        {
            Visitor.errorMessage="Field left empty";
            return false;
        }

        if (ccNum.length() != 16)
        {
            Visitor.errorMessage="Invalid Credit Card Number";
            return false;
        }

        return true;
    }

    public boolean updateAmount()
    {
        return true;
    }
}
