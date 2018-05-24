package PGL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity(name="User")
@Table(name="users")
public class User{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_id")
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

    private boolean online = false;

    private boolean checkedIn = false;

    private String checkedInPlayground = "";

    @JoinTable
    @OneToMany
    private Set<Friendship> friends = new HashSet<>();

    public User () {
        //Empty, necessary for the repository
    }

    public User (String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public boolean isOnline() {
        return online;
    }

    public String getCheckedInPlayground() {
        return checkedInPlayground;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Slumpar ett nummer mellan 0 och 5000, är det jämt sätts online till true.
     * Om online blir true så slumpas checked_in och lägger eventuellt till en lekpark
     */
    public void setRandomOnline(){
        Random rnd = new Random();
        int number = rnd.nextInt(5000);
        if (number%2 == 0){
            online = true;
        }
        else{
            online = false;
            checkedIn = false;
            checkedInPlayground = "";
        }
        if (online){
            number = rnd.nextInt(5000);
            if (number%2 == 0) {
                checkedInPlayground = "a091564e-b6d1-43fa-abfb-b1f8b400b0ff";
                checkedIn = true;
                System.out.println(name + " got a checked in playground");
            }
        }
        System.out.println(name + " " + online);

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

    public void addFriend(Friendship friend){

        friends.add(friend);
        System.out.println("friendship set: ");
        for (Friendship f : friends) {
            System.out.println(f);
        }

    }

    public Set<Friendship> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        return name;
    }

}