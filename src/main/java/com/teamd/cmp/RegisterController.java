package com.teamd.cmp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @GetMapping("/register")
    String register(@RequestParam("name") String name,@RequestParam("address") String address,@RequestParam("email") String email,@RequestParam("contact") String contact,@RequestParam("pass") String pass){
        return "Entered Data is "+name+" "+address+" "+email+" "+contact+" "+pass;
    }
}
