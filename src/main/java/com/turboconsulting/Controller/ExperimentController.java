package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;

@Controller
public class ExperimentController {


    @Autowired
    private ConsentService consentService;

    @GetMapping("/experiment")
    public String homePage(Model m,
                           @ModelAttribute("uname") String uname,
                           @ModelAttribute("experimentID") String experimentID) {
        if (uname.equals(""))  return "redirect:/login";
        int eID = Integer.parseInt(experimentID);
        m.addAttribute("consentValue", consentService.getExperimentConsent(uname, eID));
        m.addAttribute("eDetails", consentService.getExperiment(eID));


        return "experiment";
    }

    @PostMapping("/experiment/updateConsent")
    public ModelAndView updateConsent(@ModelAttribute("uname") String uname,
                                @ModelAttribute("consent") String c,
                                @ModelAttribute("eID") int eID,
                                      RedirectAttributes redir)  {
        consentService.updateExperimentConsent(uname, ConsentLevel.fromString(c), eID);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/home");
        redir.addFlashAttribute("uname", uname);

        return mav;
    }
}
