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
public class SettingsController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/{aID}/settings")
    public String homePage(Model m,
                           @PathVariable("aID") int aID) {
        //if (uname.equals(""))  return "redirect:/login";
        m.addAttribute("consentOptions", ConsentLevel.values());
        m.addAttribute("visitors", consentService.getAccountsVisitors(aID));

        return "settings";
    }

    @PostMapping("/{aID}/settings/updateConsent")
    public ModelAndView updateConsent(@PathVariable("aID") int aID,
                                      @ModelAttribute("consent") String c)  {
        consentService.updateAccountConsent(aID, ConsentLevel.fromString(c));
        ModelAndView m = new ModelAndView();
        m.setViewName("redirect:/"+ aID +"/settings");
        return m;
    }
}
