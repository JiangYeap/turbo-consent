package com.turboconsulting.Controller;

import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExperimentsController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/{uname}/experiments")
    public String experimentsPage(Model m,
                           @PathVariable(value="uname") String uname) {

        //if (uname.equals(""))  return "redirect:/login";
        m.addAttribute("experiments", consentService.getVisitorExperiments(uname));
        return "experiments";
    }


}
