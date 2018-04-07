package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SettingsController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/settings")
    public String homePage(Model m,
                           @RequestParam("aID") int aID,
                           @RequestParam(value = "update", required = false) boolean updateSuccess) {
        //if (uname.equals(""))  return "redirect:/login";
        m.addAttribute("consentOptions", ConsentLevel.values());
        m.addAttribute("visitors", consentService.getAccountsVisitors(aID));
        m.addAttribute("aID", aID);
        m.addAttribute("updateSuccess", updateSuccess);


        return "settings";
    }

    @PostMapping("/settings/updateConsent")
    public ModelAndView updateConsent(@RequestParam("aID") int aID,
                                      @ModelAttribute("consent") String c)  {
        boolean updateSuccessful = consentService.updateAccountConsent(aID, ConsentLevel.fromString(c));
        ModelAndView m = new ModelAndView();
        m.setViewName("redirect:/settings?aID="+aID+"&update="+updateSuccessful);
        return m;
    }
}
