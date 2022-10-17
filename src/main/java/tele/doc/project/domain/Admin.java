package tele.doc.project.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Admin extends Person
{
    public Admin(){}

    public Admin(String name, String email, String username, String password, String address) {
        super(name, email, username, password, address);
    }

    @Override
    public String toString() {
        return "Admin{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
