package com.teamd.cmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    RelationshipManagerRepository relationshipManagerRepository;
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("search")
    String search(@RequestParam("type") int type,@RequestParam("id") int id,@RequestParam("queryid") int queryid,@RequestParam("token") String token){
        if(type==1){
            if(relationshipManagerRepository.findById(id).isPresent()){
                RelationshipManager r=relationshipManagerRepository.findById(id).get();
                if(token.equals(r.getToken())){
                    if(customerRepository.findById(queryid).isPresent()){
                        String generatedString = TokenGenerator.newToken();
                        r.setToken(generatedString);
                        relationshipManagerRepository.save(r);
                        Customer c=customerRepository.findById(queryid).get();
                        String content = LogInController.getFileContent("searchdetails.html");
                        content=content.replace("ResultId",String.valueOf(c.getId()));
                        content=content.replace("ResultName",String.valueOf(c.getName()));
                        content=content.replace("ResultAddress",c.getAddress());
                        content=content.replace("ResultContact",c.getContact());
                        content=content.replace("ResultEmail",c.getEmail());
                        content=content.replace("DeliveredType",String.valueOf(type));
                        content=content.replace("DeliveredId",String.valueOf(id));
                        content=content.replace("DeliveredToken",generatedString);
                        return content;
                    }else {
                        return "ID not Found!!";
                    }
                }
            }
        }
        return LogInController.returnHome();
    }


}
