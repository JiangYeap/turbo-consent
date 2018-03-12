package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
        m.addAttribute("consentOptions", ConsentLevel.values());

        return "profile";
    }

    @PostMapping("/profile/updateConsent")
    public String updateConsent(@ModelAttribute("uname") String uname,
                                @ModelAttribute("consent") String c)  {
        consentService.updateConsent(uname, ConsentLevel.fromString(c));
        return "profile";
    }
}
