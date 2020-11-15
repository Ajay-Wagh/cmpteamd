package com.teamd.cmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                return "Your Id is "+customerRepository.fetchCustomers(contact).get(0).getId();
            }

        }
        return "Entered Data is "+name+" "+address+" "+email+" "+contact+" "+pass;
    }

}
