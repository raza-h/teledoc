package tele.doc.project.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "dc")
public abstract class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected String name;
    protected String email;
    protected String username;
    protected String password;
    protected String address;

    public Person()
    {}

    public Person(String name, String email, String username, String password, String address) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Review> viewReviews(Doctor D) {
        Set<Review> a = new HashSet<>();
        return a;
    }

    public float viewRating(Doctor d) {
        return d.getRating();
    }
}
