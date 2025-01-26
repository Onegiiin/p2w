package by.kapinskiy.p2w.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    @GetMapping("/test")
    public String test() {
        return "test";    }
}
