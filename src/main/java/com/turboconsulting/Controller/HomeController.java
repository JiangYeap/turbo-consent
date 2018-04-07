package com.turboconsulting.Controller;

import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("aID")
public class HomeController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/")
    public String homePage(ModelMap m) {
        int aID = 1;
        m.addAttribute("aID", aID);
        m.addAttribute("experimentsPending", consentService.getAccount(aID).getTotalPendingExperiments());
        return "home";
    }

}
