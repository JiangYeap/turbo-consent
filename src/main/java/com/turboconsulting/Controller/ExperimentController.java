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

    @GetMapping("/{aID}/visitors/{vID}/experiments/{eID}")
    public String experimentPage(Model m,
                                 @PathVariable("aID") int aID,
                                 @PathVariable("vID") int vID,
                                 @PathVariable("eID") int eID) {

        m.addAttribute("consentValue", consentService.getExperimentConsent(vID, eID));
        m.addAttribute("eDetails", consentService.getExperiment(eID));


        return "experiment";
    }

    @PostMapping("/{aID}/visitors/{vID}/experiments/{eID}/updateConsent")
    public ModelAndView updateConsent(@PathVariable("aID") int aID,
                                      @PathVariable("vID") int vID,
                                      @PathVariable("eID") int eID,
                                      @ModelAttribute("consent") String c)  {

        consentService.updateExperimentConsent(vID, ConsentLevel.fromString(c), eID);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/"+ aID + "/visitors/" + vID + "/experiments");

        return mav;
    }
}
