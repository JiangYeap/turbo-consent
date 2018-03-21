package com.turboconsulting.Controller;

import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VisitorsController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/{aID}/visitors")
    public String visitorsPage(Model m,
                               @PathVariable(value="aID") int aID)  {
        m.addAttribute("visitors", consentService.getAccountsVisitors(aID));
        return "visitors";
    }
}
