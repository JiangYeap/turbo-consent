package com.turboconsulting.Controller;

import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ConsentService consentService;

    @RequestMapping(value = "/checkDetails", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public boolean checkLoginDetails(@RequestBody LoginDetails loginDetails)  {
        return consentService.checkLoginDetails(loginDetails.uname, loginDetails.pword);
    }
}
