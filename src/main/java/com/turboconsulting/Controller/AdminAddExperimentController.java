package com.turboconsulting.Controller;

import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.ConsentOption;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Security.MyUser;
import com.turboconsulting.Security.MyUserDetailsService;
import com.turboconsulting.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;

@Controller
public class AdminAddExperimentController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailsService userService;

    @GetMapping("/admin/experiments")
    public String adminExperimentsPage(ModelMap m) {
        m.addAttribute("experiments", adminService.getAllExperiments());

        int aID = getLoggedInAccountID();
        return "admin-experiments";
    }

    @PostMapping("/admin/experiments/add")
    public ModelAndView addAccount(@ModelAttribute("name") String name,
                                   @ModelAttribute("description") String description,
                                   @ModelAttribute("consentNames") List<String> consentNames,
                                   @ModelAttribute("consentDescriptions") List<String> consentDescriptions)  {
        HashSet<ConsentOption> consentOptions = new HashSet<>();
        for(int i = 0; i < consentNames.size(); i++)  consentOptions.add(new ConsentOption(consentNames.get(i),
                                                                                           consentDescriptions.get(i)));


        adminService.addNewExperiment(new Experiment(name, description),  consentOptions);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/admin/experiments");
        return mav;
    }

    private int getLoggedInAccountID() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUser userDetails = (MyUser)userService.loadUserByUsername(auth.getName());
        return userDetails.getUser().getAccountId();
    }
}
