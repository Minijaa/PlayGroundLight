package PGL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    String login (String email, String password) {
        User theUser = userRepository.findByEmail(email);
        if (theUser == null) {
            return "No user with that email";
        }

        if (!theUser.getPassword().equals(password)) {
            return "Wrong password motherfucker";
        }

        else
           return "logged in";
    }

}