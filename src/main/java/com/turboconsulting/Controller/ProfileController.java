package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ProfileController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/{vID}/profile")
    public String homePage(Model m,
                           @PathVariable("vID") int vID) {
        //if (uname.equals(""))  return "redirect:/login";
        m.addAttribute("consentOptions", ConsentLevel.values());

        return "profile";
    }

    @PostMapping("/{vID}/profile/updateConsent")
    public ModelAndView updateConsent(@PathVariable("uname") int vID,
                                      @ModelAttribute("consent") String c)  {
        consentService.updateVisitorConsent(vID, ConsentLevel.fromString(c));
        ModelAndView m = new ModelAndView();
        m.setViewName("redirect:/"+ vID +"/profile");
        return m;
    }
}
