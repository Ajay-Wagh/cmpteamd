package com.teamd.cmp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchAndDeleteController {

    @Autowired
    CompanyOperatorRepository companyOperatorRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerDeletedRepository customerDeletedRepository;


    @PostMapping("/searchanddelete")
    String search(@RequestParam("type") int type, @RequestParam("id") int id, @RequestParam("queryid") int queryid, @RequestParam("token") String token) throws JsonProcessingException {
        if(type==2){
            if(companyOperatorRepository.findById(id).isPresent()){
                CompanyOperator c=companyOperatorRepository.findById(id).get();
                if(token.equals(c.getToken())){
                    if(customerRepository.findById(queryid).isPresent()){
                        String generatedString = TokenGenerator.newToken();
                        //c.setToken(generatedString);
                        companyOperatorRepository.save(c);
                        Customer cm=customerRepository.findById(queryid).get();
                        String content = LogInController.getFileContent("searchanddelete.html");
                        content=content.replace("ResultId",String.valueOf(cm.getId()));
                        content=content.replace("ResultName",String.valueOf(cm.getName()));
                        content=content.replace("ResultAddress",cm.getAddress());
                        content=content.replace("ResultContact",cm.getContact());
                        content=content.replace("ResultEmail",cm.getEmail());
                        content=content.replace("DeliveredType",String.valueOf(type));
                        content=content.replace("DeliveredId",String.valueOf(id));
                        content=content.replace("DeliveredToken",generatedString);
                        ObjectMapper mapper = new ObjectMapper();
                        //Converting the Object to JSONString
                        String jsonString = mapper.writeValueAsString(cm);
                        return jsonString;
                    }else {
                        return "{\"error\":\"Id Not Found\"}";
                    }
                }
            }
        }
        return LogInController.returnHome();
    }

    @PostMapping("/searchanddelete_delete")
    String searchAndDelete(@RequestParam("type") int type, @RequestParam("id") int id, @RequestParam("deleteid") int queryid, @RequestParam("token") String token){
        if(type==2){
            if(companyOperatorRepository.findById(id).isPresent()){
                CompanyOperator c=companyOperatorRepository.findById(id).get();
                if(token.equals(c.getToken())){
                    if(customerRepository.findById(queryid).isPresent()){
                        Customer cm=customerRepository.findById(queryid).get();
                        customerDeletedRepository.save(getCustomerDeleted(cm));
                        customerRepository.delete(cm);
                        return "Data Deleted for Id : "+ queryid;
                    }else {
                        return "ID not Found!!";
                    }
                }
            }
        }
        return "hi";
    }

    CustomerDeleted getCustomerDeleted(Customer c){
        CustomerDeleted cd=new CustomerDeleted();
        cd.setId(c.getId());
        cd.setContact(c.getContact());
        cd.setEmail(c.getEmail());
        cd.setName(c.getName());
        cd.setToken(c.getToken());
        cd.setPassword(c.getPassword());
        cd.setAddress(c.getAddress());
        return cd;
    }


}
