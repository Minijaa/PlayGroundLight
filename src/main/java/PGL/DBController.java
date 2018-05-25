package PGL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8100", "10.200.3.220", "file://", "http://localhost:8000", "127.0.0.1", "http://192.168.0.14:8000", "http://192.168.0.14:8080", "http://192.168.0.14:80", "http://192.168.0.17:8000", "http://192.168.0.17:80", "http://10.200.3.220:8000", "https://www.musikshopen.com:8000", "https://www.musikshopen.com", "https://www.musikshopen.com:8100", "https://www.musikshopen.com:443"})
@RequestMapping("/db")
public class DBController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;


    @GetMapping("/dbadd")
    public @ResponseBody
    StringResponse addNewUser (@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        User newUser = new User(name, email, password);
        User theUser = userRepository.findByEmail(email);
        if(theUser != null){
            return new StringResponse("A user with this email adress already exists.");
        } else {
            userRepository.save(newUser);
            return new StringResponse("Saved with id: " + newUser.getId());
        }
    }

    @GetMapping("/get")
    public @ResponseBody
    StringResponse getByEmail(@RequestParam String email) {
        User theUser = userRepository.findByEmail(email);
        if (theUser == null) {
            return new StringResponse("User doesn't exist");
        }
        return new StringResponse(theUser.getName());
    }

    @GetMapping("/getUser")
    public @ResponseBody
    UserJson getUser(@RequestParam String email) {
        User temp = userRepository.findByEmail(email);
        if (temp == null) {
            return new UserJson();
        }
        UserJson theUser = new UserJson(temp);
        return theUser;
    }


    @GetMapping("/search")
    public @ResponseBody
    UserJson[] getByName(@RequestParam String name) {
        User[]theUsers = userRepository.findByNameStartingWith(name);

        if (theUsers == null || theUsers[0] == null){
            return new UserJson[0];
        }

        UserJson[] results = new UserJson[theUsers.length];

        for (int i = 0 ; i < theUsers.length ; i++){
                User tempUser = theUsers[i];
                results[i] = new UserJson(tempUser);

        }

        return results;
    }

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path="/login")
    public @ResponseBody
    StringResponse login (String email, String password) {
        User theUser = userRepository.findByEmail(email);
        if (theUser == null || !theUser.getPassword().equals(password)) {
            return new StringResponse("User or password incorrect");
        }
        return new StringResponse("logged in");
    }

    @GetMapping(path="/addFriend")
    public @ResponseBody
    StringResponse addFriend(String emailOne, String emailTwo) {
        User one = userRepository.findByEmail(emailOne);
        User two = userRepository.findByEmail(emailTwo);

        if (one == null || two == null) {
            return new StringResponse("User doesn't exist");
        }

        Friendship friendship_one = new Friendship(one, two);
        Friendship friendship_two = new Friendship(two, one);
        one.addFriend(friendship_one);
        friendshipRepository.save(friendship_one);
        two.addFriend(friendship_two);
        friendshipRepository.save(friendship_two);
        return new StringResponse("added");
    }

    @GetMapping(path="/changePassword")
    public void changePassword(@RequestParam String email, @RequestParam String password){
        User theUser = userRepository.findByEmail(email);
        if (theUser != null){
            theUser.setPassword(password);
            System.out.println(theUser + " changed");
            userRepository.save(theUser);
        }
    }

    @GetMapping(path="/getFriends")
    public @ResponseBody
    UserJson[] getFriends(String email) {
        User theUser = userRepository.findByEmail(email);

        if (theUser == null) {
            return new UserJson[0];
        }

        Set<Friendship> temp = theUser.getFriends();
        Friendship[] friends = temp.toArray(new Friendship[temp.size()]);
        UserJson[] jsonFriends = new UserJson[friends.length];

        for (int i = 0 ; i < friends.length ; i++) {
            User tempUser = friends[i].getFriend();
            jsonFriends[i] = new UserJson(tempUser);
        }

        return jsonFriends;
    }


    @GetMapping(path="/random")
    public @ResponseBody
    StringResponse random(){
        Iterable<User> theUsers = userRepository.findAll();

        for (User u : theUsers){
            u.setRandomOnline();
        }

        userRepository.saveAll(theUsers);

        return new StringResponse("blandat");
    }

}