package com.group05.playground;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class HelloController {
    private static final String template = "Hello, %s!";

    @RequestMapping("/")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "Pelle") String name) {
        return new Greeting(String.format(template, name));
    }

}
