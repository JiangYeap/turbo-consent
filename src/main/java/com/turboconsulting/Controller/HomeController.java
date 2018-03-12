package com.turboconsulting.Controller;

import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/home")
    public String homePage(Model m, @ModelAttribute("uname") String uname) {
        if (uname .equals(""))  return "redirect:/login";
        m.addAttribute("experiments", consentService.getVisitorExperiments(uname));
        return "home";
    }
}
