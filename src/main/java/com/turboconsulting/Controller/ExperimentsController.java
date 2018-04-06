package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.VisitorExperiment;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExperimentsController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/{aID}/visitors/{vID}/experiments")
    public String experimentsPage(Model m,
                           @PathVariable(value="aID") int aID,
                           @PathVariable(value="vID") int vID  ) {

        m.addAttribute("visitorExps", consentService.getVisitorExperiments(vID));
        m.addAttribute("visitorName", consentService.getVisitor(vID).getName());

        return "experiments";
    }

    @PostMapping("/{aID}/visitors/{vID}/experiments/updateConsent")
    public ModelAndView updateConsent(@PathVariable("aID") int aID,
                                      @PathVariable("vID") int vID,
                                      @ModelAttribute("selected") List<Integer> eIDs,
                                      @ModelAttribute("consentLevel") String c)  {

        for (int eID : eIDs) consentService.updateExperimentConsent(vID, ConsentLevel.fromString(c), eID);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/"+ aID + "/visitors/" + vID + "/experiments");

        return mav;
    }
}
