package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ExperimentController {


    @Autowired
    private ConsentService consentService;

    @GetMapping("/visitors/experiments/experiment")
    public String experimentPage(Model m,
                                 @RequestParam("aID") int aID,
                                 @RequestParam("vID") int vID,
                                 @RequestParam("eID") int eID) {

        m.addAttribute("visitorExp", consentService.getVisitorExperiment(vID, eID));
        m.addAttribute("visitorName", consentService.getVisitor(vID).getName());

        return "experiment";
    }

    @PostMapping("/visitor/experiments/updateConsent")
    public ModelAndView updateConsent(@RequestParam("aID") int aID,
                                      @RequestParam("vID") int vID,
                                      @RequestParam("eID") int eID,
                                      @ModelAttribute("consentLevel") String c)  {

        consentService.updateExperimentConsent(vID, ConsentLevel.fromString(c), eID);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/visitors/experiments?aID="+aID+"&vID="+vID);

        return mav;
    }
}
