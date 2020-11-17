package com.teamd.cmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/update")
    String update(@RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("email") String email,
                  @RequestParam("contact") String contact, @RequestParam("pass") String pass,@RequestParam("token") String token,@RequestParam("id") int id){

       if(customerRepository.findById(id).isPresent()){
           Customer c=customerRepository.findById(id).get();
           if(c.getToken().equals(token)){
               if(contact.equals(c.getContact())){
                   updateDetails(c,name,address,contact,pass,TokenGenerator.newToken(),email);
               }else if(customerRepository.fetchCustomers(contact).isEmpty()){
                   updateDetails(c,name,address,contact,pass,TokenGenerator.newToken(),email);
               } else {
                   return "Contact Number Already Present.. Try Different";
               }
               //could let user stay
               return "Details Updated.. kindly log in again.. <a href=\"customer_login.html\">LogIn</a>";
           }
       }

        return "Something Went Wrong..., Try Again";
    }



    private void updateDetails(Customer c,String name, String address,String contact, String pass, String token,String email){
        c.setName(name);
        c.setPassword(pass);
        c.setEmail(email);
        c.setAddress(address);
        c.setContact(contact);
        c.setToken(token);
        customerRepository.save(c);
    }




}
