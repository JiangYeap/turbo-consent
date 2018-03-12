package com.turboconsulting.Controller;

import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private ConsentService consentService;

    @GetMapping("/home")
    public String homePage(Model m,
                           @ModelAttribute("uname") String uname) {
        if (uname .equals(""))  return "redirect:/login";
        m.addAttribute("experiments", consentService.getVisitorExperiments(uname));
        return "home";
    }

    @PostMapping("/profile")
    public ModelAndView profileSettings(@RequestParam("uname") String uname,
                                         RedirectAttributes redir)  {
        ModelAndView m = new ModelAndView();
        m.setViewName("redirect:/profile");
        redir.addFlashAttribute("uname", uname);
        return m;
    }


    @PostMapping("/experiment")
    public ModelAndView selectExperiment(@RequestParam("experimentID") int experimentID,
                                         @RequestParam("uname") String uname,
                                         RedirectAttributes redir)  {
        ModelAndView m = new ModelAndView();
        m.setViewName("redirect:/experiment");
        redir.addFlashAttribute("experimentID", experimentID);
        redir.addFlashAttribute("uname", uname);

        return m;
    }
}
