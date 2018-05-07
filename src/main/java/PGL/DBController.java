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
    String addNewUser (@RequestParam String name, @RequestParam String e) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(e);
        userRepository.save(newUser);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

}