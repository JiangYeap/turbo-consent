package com.turboconsulting.Controller;

import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


@Controller
public class ProfileController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/profile")
    public String homePage(Model m,
                           @ModelAttribute("uname") String uname) {
        if (uname.equals(""))  return "redirect:/login";


        return "profile";
    }

}
