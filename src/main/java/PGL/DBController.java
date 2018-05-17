package PGL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/db")
public class DBController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/dbadd")
    public @ResponseBody
    String addNewUser (@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        User newUser = new User(name, email, password);
        userRepository.save(newUser);
        return "Saved with id: " + newUser.getId();
    }

    @GetMapping("/get")
    public @ResponseBody
    String getByEmail(String email) {
        User theUser = userRepository.findByEmail(email);
        return theUser.getName();
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
        return one.isFriend(two);
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