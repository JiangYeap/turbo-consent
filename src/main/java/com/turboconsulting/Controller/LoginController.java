package com.turboconsulting.Controller;

import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class LoginController {

    @Autowired
    private ConsentService consentService;


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDetails", new LoginDetails());

        return "login";
    }


    @PostMapping("/login")
    public ModelAndView loginSubmit(@ModelAttribute LoginDetails loginDetails, RedirectAttributes redir) {
        ModelAndView mav = new ModelAndView();
        if(consentService.checkLoginDetails(loginDetails))  {
            mav.setViewName("redirect:/home");
            redir.addFlashAttribute("uname", loginDetails.getUname());
            return mav;
        }
        mav.setViewName("redirect:/login");
        return mav;
    }



    @RequestMapping(value = "/updatePassword", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public void updatePassword(@RequestBody LoginDetails loginDetails)  {
        consentService.updatePassword(loginDetails);
    }
}
