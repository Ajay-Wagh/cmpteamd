package com.teamd.cmp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogInController {

    @PostMapping("/login")
    String logIn(@RequestParam("type") int type,@RequestParam("id") int id,@RequestParam("pass") String pass){
        if(type==0){

        }
        else if(type==1){

        }
        else if(type==2){

        }
        return "Error , Don't Use Shortcuts...";
    }

    @PostMapping("/logout")
    String logOut(@RequestParam("type") int type,@RequestParam("id") int id){
        return "Error , Don't Use Shortcuts...";
    }


}
