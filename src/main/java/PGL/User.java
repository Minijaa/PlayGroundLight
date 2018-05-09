package PGL;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private int id;
    private String name;
    private String email;
    private String password;

    @ManyToMany
    @JoinTable (
            name="friends",
            joinColumns=@JoinColumn(name="personOne", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="personTwo", referencedColumnName = "id"))
    private List<User> friends;

    public User () {
    }

    public User (String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        id = i;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public List<User> getFriends() {
        return friends;
    }

}