package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Security.MyUser;
import com.turboconsulting.Security.MyUserDetailsService;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


@Controller
@SessionAttributes("aID")
public class SettingsController {

    @Autowired
    private ConsentService consentService;

    @Autowired
    private MyUserDetailsService userService;

    @GetMapping("/settings")
    public String homePage(ModelMap m,
                           @RequestParam(value = "update", required = false) boolean updateSuccess) {
        //if (uname.equals(""))  return "redirect:/login";
        int aID = (int) m.get("aID");
        m.addAttribute("consentOptions", ConsentLevel.values());
        m.addAttribute("visitors", consentService.getAccountsVisitors(aID));
        m.addAttribute("updateSuccess", updateSuccess);
        return "settings";
    }

    @PostMapping("/settings/updateConsent")
    public String updateConsent(ModelMap m, @ModelAttribute("consent") String c)  {
        int aID = getLoggedInAccountID();
        boolean updateSuccessful = consentService.updateAccountConsent(aID, ConsentLevel.fromString(c));
        return "redirect:/settings?update="+updateSuccessful;
    }

    private int getLoggedInAccountID() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUser userDetails = (MyUser)userService.loadUserByUsername(auth.getName());
        return userDetails.getUser().getAccountId();
    }
}
