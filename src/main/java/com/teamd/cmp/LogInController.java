package com.teamd.cmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@RestController
public class LogInController {
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/login")
    String logIn(@RequestParam("type") int type,@RequestParam("id") int id,@RequestParam("pass") String pass){
        if(type==0){
            if(customerRepository.findById(id).isPresent()){
                Customer c=customerRepository.findById(id).get();
                if(pass.equals(c.getPassword())) {
                    String generatedString = TokenGenerator.newToken();
                    c.setToken(generatedString);
                    customerRepository.save(c);

                    StringBuilder contentBuilder = new StringBuilder();
                    try {
                        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Ajay\\cmpteamd\\src\\main\\resources\\static\\update.html"));
                        String str;
                        while ((str = in.readLine()) != null) {
                            contentBuilder.append(str);
                        }
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String content = contentBuilder.toString();
                    content=content.replace("DeliveredId",String.valueOf(c.getId()));
                    content=content.replace("DeliveredName",String.valueOf(c.getName()));
                    content=content.replace("DeliveredToken",generatedString);
                    content=content.replace("DeliveredEmail",String.valueOf(c.getEmail()));
                    content=content.replace("DeliveredContact",String.valueOf(c.getContact()));
                    content=content.replace("DeliveredPassword",String.valueOf(c.getPassword()));
                    content=content.replace("DeliveredAddress",String.valueOf(c.getAddress()));
                    content=content.replace("DeliveredType",String.valueOf(type));
                    return content;
                }
            }
        }


        return "Error , Wrong Credentials";
    }

    @PostMapping("/logout")
    String logOut(@RequestParam("type") int type,@RequestParam("id") int id,@RequestParam("token")String token){
        if (type == 0) {
            if(customerRepository.findById(id).isPresent()){
                Customer c=customerRepository.findById(id).get();
                if(c.getToken().equals(token)) {
                    c.setToken(TokenGenerator.newToken());
                    return "<head>\n" +
                            "  <meta http-equiv='refresh' content='0; URL=index.html'>\n" +
                            "</head>";
                }
            }
        }


        return "Error , Something Went Wrong...";
    }


}
