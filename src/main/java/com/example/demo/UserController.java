package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


    @Autowired
    public UserController(UserService userService){
        this.userService= userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String item) {
        return userService.registerUser(item);
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "Woorld") String name) {
        return new Greeting(counter.incrementAndGet(), template.formatted(name));
    }

    @GetMapping("/allusernames")
    public List<String> allusernames() {
        return userService.allUserNames();
    }
}