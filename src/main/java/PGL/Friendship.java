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


    @Access(AccessType.PROPERTY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userOne_id")
    private User userOne;


    @Access(AccessType.PROPERTY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userTwo_id")
    private User userTwo;

    public Friendship(){

    }

    public Friendship(User one, User two) {
        userOne = one;
        userTwo = two;
    }

    public int getId(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

    public void setUserOne(User u){
        userOne = u;
    }
    public void setUserTwo(User u){
        userOne = u;
    }


    public User getUserOne(){
        return userOne;
    }

    public User getUserTwo(){
        return userTwo;
    }

    public String toString(){
        return userOne + ", " + userTwo;
    }

}
