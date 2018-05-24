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

    @GetMapping("/search")
    public @ResponseBody
    StringResponse[] getByName(@RequestParam String name) {
        User[]theUser = userRepository.findByName(name);
        StringResponse[] users = new StringResponse[theUser.length];

        for (int i = 0 ; i < theUser.length ; i++){
            System.out.println(theUser[i]);
            StringResponse str = new StringResponse(theUser[i].getName());
            users[i] = str;
        }

        return users;
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
    String addFriend(String emailOne, String emailTwo) {
        User one = userRepository.findByEmail(emailOne);
        User two = userRepository.findByEmail(emailTwo);

        if (one == null || two == null) {
            return "User doesn't exist";
        }

        Friendship friendship_one = new Friendship(one, two);
        Friendship friendship_two = new Friendship(two, one);
        one.addFriend(friendship_one);
        friendshipRepository.save(friendship_one);
        two.addFriend(friendship_two);
        friendshipRepository.save(friendship_two);
        return "added";
    }


    @GetMapping(path="/getFriends")
    public @ResponseBody
    String getFriends(String email) {
        User theUser = userRepository.findByEmail(email);

        if (theUser == null) {
            return "User doesn't exist";
        }

        StringBuilder str = new StringBuilder();

        for (Friendship f : theUser.getFriends()) {
            str.append(f + ", ");
        }

        return str.toString();
    }

    @GetMapping(path = "/online")
    public @ResponseBody
    StringResponse[] getOnlineFriends(@RequestParam String email){

        User theUser = userRepository.findByEmail(email);

        if (theUser == null){
            return new StringResponse[0];
        }

        Set<Friendship> temp = theUser.getFriends();
        Friendship[] friends = temp.toArray(new Friendship[temp.size()]);
        ArrayList<StringResponse> onlineFriends = new ArrayList<>();

        for (int i = 0 ; i < friends.length ; i++) {

            if (friends[i].getFriend().isOnline()) {
                onlineFriends.add(new StringResponse(""+friends[i].getFriend()));
            }
        }

        return onlineFriends.toArray(new StringResponse[onlineFriends.size()]);
    }

    @GetMapping(path = "/offline")
    public @ResponseBody
    StringResponse[] getOfflineFriends(@RequestParam String email){

        User theUser = userRepository.findByEmail(email);

        if (theUser == null){
            return new StringResponse[0];
        }

        Set<Friendship> temp = theUser.getFriends();
        Friendship[] friends = temp.toArray(new Friendship[temp.size()]);
        ArrayList<StringResponse> offlineFriends = new ArrayList<>();

        for (int i = 0 ; i < friends.length ; i++) {

            if (!friends[i].getFriend().isOnline()) {
                offlineFriends.add(new StringResponse(""+friends[i].getFriend()));
            }
        }

        return offlineFriends.toArray(new StringResponse[offlineFriends.size()]);
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