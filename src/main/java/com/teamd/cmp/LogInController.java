package com.teamd.cmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


@RestController
public class LogInController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RelationshipManagerRepository relationshipManagerRepository;
    @Autowired
    CompanyOperatorRepository companyOperatorRepository;

    @PostMapping("/login")
    String logIn(@RequestParam("type") int type,@RequestParam("id") int id,@RequestParam("pass") String pass){
        if(type==0){
            if(customerRepository.findById(id).isPresent()){
                Customer c=customerRepository.findById(id).get();
                if(pass.equals(c.getPassword())) {
                    String generatedString = TokenGenerator.newToken();
                    c.setToken(generatedString);
                    customerRepository.save(c);
                    String content = getFileContent("update.html");
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
            return "Wrong Credentials...";
        }
        else if(type==1){
            if(relationshipManagerRepository.findById(id).isPresent()){
                RelationshipManager r=relationshipManagerRepository.findById(id).get();
                if(pass.equals(r.getPassword())){
                    String generatedString = TokenGenerator.newToken();
                    r.setToken(generatedString);
                    relationshipManagerRepository.save(r);
                    String content = getFileContent("searchdetails.html");
                    content=content.replace("DeliveredType",String.valueOf(type));
                    content=content.replace("DeliveredId",String.valueOf(id));
                    content=content.replace("DeliveredToken",generatedString);
                    return content;

                }
            }
            return "Wrong Credentials...";
        }
        else if(type==2){
            if(companyOperatorRepository.findById(id).isPresent()){
                CompanyOperator c=companyOperatorRepository.findById(id).get();
                if(pass.equals(c.getPassword())){
                    String generatedString = TokenGenerator.newToken();
                    c.setToken(generatedString);
                    companyOperatorRepository.save(c);
                    String content = getFileContent("searchanddelete.html");
                    content=content.replace("DeliveredType",String.valueOf(type));
                    content=content.replace("DeliveredId",String.valueOf(id));
                    content=content.replace("DeliveredToken",generatedString);
                    return content;

                }
            }
            return "Wrong Credentials...";
        }


        return "Error , Something Went Wrong..";
    }

    @PostMapping("/logout")
    String logOut(@RequestParam("type") int type,@RequestParam("id") int id,@RequestParam("token")String token){
        if (type == 0) {
            if(customerRepository.findById(id).isPresent()){
                Customer c=customerRepository.findById(id).get();
                if(c.getToken().equals(token)) {
                    c.setToken(TokenGenerator.newToken());
                    customerRepository.save(c);
                    return returnHome();
                }
            }
        }
        else if(type==1){
            if(relationshipManagerRepository.findById(id).isPresent()){
                RelationshipManager r=relationshipManagerRepository.findById(id).get();
                if(r.getToken().equals(token)) {
                    r.setToken(TokenGenerator.newToken());
                    relationshipManagerRepository.save(r);
                    return returnHome();
                }
            }
        }
        else if(type==2){
            if(companyOperatorRepository.findById(id).isPresent()){
                CompanyOperator c=companyOperatorRepository.findById(id).get();
                if(c.getToken().equals(token)) {
                    c.setToken(TokenGenerator.newToken());
                    companyOperatorRepository.save(c);
                    return returnHome();
                }
            }
        }


        return "Error , Something Went Wrong...";
    }


    static String getFileContent(String fileName){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Ajay\\cmpteamd\\src\\main\\resources\\static\\"+fileName));
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

    static String returnHome(){
        return "<head>\n" +
                "  <meta http-equiv='refresh' content='0; URL=index.html'>\n" +
                "</head>";
    }

}
