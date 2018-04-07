package com.turboconsulting.Controller;

import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("aID")
public class VisitorsController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/visitors")
    public String visitorsPage(ModelMap m)  {
        int aID = (int) m.get("aID");
        m.addAttribute("visitors", consentService.getAccountsVisitors(aID));
        return "visitors";
    }
}
