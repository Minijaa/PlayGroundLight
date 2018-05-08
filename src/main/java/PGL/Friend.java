package PGL;


import javax.persistence.*;

/**
 * Hello friend
 */

@Entity
@Table(name="friends")
public class Friend {

    private User personOne;
    private User personTwo;

    public Friend () {

    }

    public Friend (User personOne, User personTwo) {
        this.personOne = personOne;
        this.personTwo = personTwo;
    }

}
