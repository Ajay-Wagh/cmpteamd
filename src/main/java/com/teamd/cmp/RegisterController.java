package com.teamd.cmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class RegisterController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register")
    String register(@RequestParam("name") String name,@RequestParam("address") String address,@RequestParam("email") String email,@RequestParam("contact") String contact,@RequestParam("pass") String pass){
        if(name.length()>0 && address.length()>0 && contact.length()>0 && pass.length()>0 && email.length()>0){
            if(customerRepository.fetchCustomers(contact).isEmpty()){
                Customer c=new Customer();
                c.setContact(contact);
                c.setAddress(address);
                c.setEmail(email);
                c.setName(name);
                c.setPassword(pass);
                c.setToken(TokenGenerator.newToken());
                customerRepository.save(c);
                int id=customerRepository.fetchCustomers(contact).get(0).getId();
                String content = getFileContent("registration_divert.html");
                content=content.replace("DeliveredID",String.valueOf(id));
                return content;
            }
            return "Contact already exists..<a href=\"registration.html\">click here</a> to register again.";

        }
        return "Something Went Wrong...";
    }

    static String getFileContent(String fileName){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Shubham\\Desktop\\Dispur Wireless\\back\\cmpteamd\\src\\main\\resources\\static\\"+fileName));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

}
