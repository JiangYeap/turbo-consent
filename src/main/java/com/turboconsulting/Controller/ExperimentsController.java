package com.turboconsulting.Controller;

import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.VisitorExperiment;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("aID")
public class ExperimentsController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/visitors/experiments")
    public String experimentsPage(ModelMap m,
                                  @RequestParam(value="vID") int vID,
                                  @RequestParam(value="update", required = false) boolean updateSuccess) {
        m.addAttribute("visitorExps", consentService.getVisitorExperiments(vID));
        m.addAttribute("visitorName", consentService.getVisitor(vID).getName());
        m.addAttribute("vID", vID);
        m.addAttribute("updateSuccess", updateSuccess);

        return "experiments";
    }

    @PostMapping("/visitors/experiments/updateConsent")
    public String updateConsent(ModelMap m, @RequestParam("vID") int vID,
                                      @ModelAttribute("selected") List<Integer> eIDs,
                                      @ModelAttribute("consentLevel") String c)  {
        boolean updateSuccessful = consentService.updateBatchExperimentConsents(vID, ConsentLevel.fromString(c), eIDs);
        return "redirect:/visitors/experiments?vID="+vID+"&update="+updateSuccessful;
    }
}
