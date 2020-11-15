package com.teamd.cmp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {
    @PostMapping("/update")
    String update(){
        return "hi";
    }
}
