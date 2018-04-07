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
                                 @RequestParam("eID") int eID,
                                 @RequestParam(value = "update", required = false) boolean updateSuccess) {
        m.addAttribute("visitorExp", consentService.getVisitorExperiment(vID, eID));
        m.addAttribute("visitorName", consentService.getVisitor(vID).getName());
        m.addAttribute("aID", aID);
        m.addAttribute("vID", vID);
        m.addAttribute("eID", eID);
        m.addAttribute("updateSuccess", updateSuccess);

        return "experiment";
    }

    @PostMapping("/visitor/experiments/updateConsent")
    public ModelAndView updateConsent(@RequestParam("aID") int aID,
                                      @RequestParam("vID") int vID,
                                      @RequestParam("eID") int eID,
                                      @ModelAttribute("consentLevel") String c)  {
        boolean updateSuccessful = false;
        if(consentService.updateExperimentConsent(vID, ConsentLevel.fromString(c), eID))  {
            updateSuccessful = true;
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/visitors/experiments?aID="+aID+"&vID="+vID+"&update="+updateSuccessful);
        return mav;
    }
}
