package PGL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return new StringResponse(theUser.getName());
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
        one.addFriend(two);
        two.addFriend(one);
        return "added";
    }

    @GetMapping(path="/friend")
    public @ResponseBody
    boolean isFriend(String emailOne, String emailTwo) {
        User one = userRepository.findByEmail(emailOne);
        User two = userRepository.findByEmail(emailTwo);
        return two.isFriend(one);
    }

//    @GetMapping(path="/getFriends")
//    public @ResponseBody
//    String getFriends(String email) {
//        User theUser = userRepository.findByEmail(email);
//        System.out.println("the user: " + theUser.getName());
//        StringBuilder str = new StringBuilder();
//
//        for (User u : theUser.getFriends()) {
//            str.append(u.getName());
//            System.out.println(u.getName());
//        }
//
//        return str.toString();
//    }

}