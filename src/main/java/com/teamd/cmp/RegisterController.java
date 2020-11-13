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
        if(name.length()>0 && address.length()>0 && email.length()>0 && contact.length()>0 && pass.length()>0){
            if(customerRepository.fetchCustomerByContact(contact).isEmpty())
            {
                Customer c=new Customer();
                //c.setId(0);
                c.setAddress(address);
                c.setEmail(email);
                c.setContact(contact);
                c.setName(name);
                c.setToken("NNNNNNNNN");
                c.setPassword(pass);
                customerRepository.save(c);
                return "Your Registration ID is : "+ customerRepository.fetchCustomerByContact(contact).get(0).getId();
            }
        }
        return "Entered Data is "+name+" "+address+" "+email+" "+contact+" "+pass;
    }
}
