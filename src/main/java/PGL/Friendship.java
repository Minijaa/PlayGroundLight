package PGL;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="friendship_id")
    private int id;

    @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },
            mappedBy = "friends"
    )
    private Set<User> friends = new HashSet<>();

    public Friendship(){

    }

    public void setFriend(User friend) {
       friends.add(friend);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (User u : friends) {
            str.append(u + " , ");
        }

        return str.toString();
    }
}
