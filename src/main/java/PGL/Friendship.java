package PGL;

import javax.persistence.*;

@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="friendship_id")
    private int id;


    @Access(AccessType.PROPERTY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;


    @Access(AccessType.PROPERTY)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="friend_id")
    private User friend;

    public Friendship(){

    }

    public Friendship(User theUser, User theFriend) {
        user = theUser;
        friend = theFriend;
    }

    public int getId(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

    public void setUser(User u){
        user = u;
    }
    public void setFriend(User u){
        friend = u;
    }

    public User getUser(){
        return user;
    }

    public User getFriend(){
        return friend;
    }

    public String toString(){
        return ""+ friend;
    }

}
