package PGL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity(name="User")
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @Size(max = 50)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
    )
    @JoinTable(name = "user_friendships",
    joinColumns = {  @JoinColumn(name="user_id")},
            inverseJoinColumns = { @JoinColumn(name="friend_id") })
    private Set<Friendship> friends = new HashSet<>();

    public User () {
        //Empty, necessary for the repository
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
        Friendship hej = new Friendship();
        hej.setFriend(friend);

        friends.add(hej);
        System.out.println("adding: " + friend.getName() + " as friend to " + name + "\nFriendset now contains:");
        for (Friendship f : friends) {
            System.out.println(f);
        }
    }

    public boolean isFriend(User u) {
        return friends.contains(u);
    }

    @Override
    public String toString() {
        return name;
    }

}