package com.turboconsulting.Controller;

import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/home")
    public String homePage(Model m,
                           @RequestParam(value="aID") int accountID) {
        m.addAttribute("experimentsPending", consentService.getAccount(accountID).getTotalPendingExperiments());
        m.addAttribute("aID", accountID);
        return "home";
    }

}
