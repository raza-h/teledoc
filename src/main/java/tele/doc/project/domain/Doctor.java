package tele.doc.project.domain;

public class Doctor
{
    public String username;
    public String email;
    public String password;
    public String phoneNum;
    public String address;

    public Doctor() {

    }

    public Doctor(String username, String email, String password, String phoneNum, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
