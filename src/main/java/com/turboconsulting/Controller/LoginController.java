package com.turboconsulting.Controller;

import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private ConsentService consentService;


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDetails", new LoginDetails());
        return "login";
    }


    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginDetails loginDetails) {
        if(consentService.checkLoginDetails(loginDetails))  {
            return "welcome";
        }
        return "login";
    }



    @RequestMapping(value = "/updatePassword", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public void updatePassword(@RequestBody LoginDetails loginDetails)  {
        consentService.updatePassword(loginDetails);
    }
}
