package com.turboconsulting.Controller;

import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView loginSubmit(@ModelAttribute LoginDetails loginDetails) {
        ModelAndView mav = new ModelAndView();
        if(consentService.checkAccountLogin(loginDetails))  {
            mav.setViewName("redirect:/" + consentService.getAccountID(loginDetails.getEmail()) + "/home");
            return mav;
        }
        mav.setViewName("redirect:/login");
        return mav;
    }



}
