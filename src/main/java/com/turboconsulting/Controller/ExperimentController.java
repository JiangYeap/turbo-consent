package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ExperimentController {


    @Autowired
    private ConsentService consentService;

    @GetMapping("/{uname}/experiments/{eID}")
    public String experimentPage(Model m,
                           @PathVariable("eID") int eID,
                           @PathVariable("uname") String uname) {
        //if (uname.equals(""))  return "redirect:/login";
        m.addAttribute("consentValue", consentService.getExperimentConsent(uname, eID));
        m.addAttribute("eDetails", consentService.getExperiment(eID));


        return "experiment";
    }

    @PostMapping("/{uname}/experiments/{eID}/updateConsent")
    public ModelAndView updateConsent(@PathVariable("uname") String uname,
                                      @PathVariable("eID") int eID,
                                      @ModelAttribute("consent") String c)  {

        consentService.updateExperimentConsent(uname, ConsentLevel.fromString(c), eID);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/"+uname+"/experiments/");

        return mav;
    }
}
