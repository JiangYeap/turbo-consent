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

    @GetMapping("/{aID}/home")
    public String homePage(Model m,
                           @PathVariable(value="aID") int accountID) {
        m.addAttribute("experimentsPending", consentService.getAccount(accountID).getTotalPendingExperiments());

        return "home";
    }

}
