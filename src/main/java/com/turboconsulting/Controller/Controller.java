package com.turboconsulting.Controller;

import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.GregorianCalendar;

@RestController
@RequestMapping("/data")
public class Controller {

    @Autowired
    private ConsentService consentService;


    @GetMapping(path = "/visitors")
    public Collection<Visitor> getAllStudents() {
        return consentService.getAllVisitors();
    }

    @GetMapping(path = "/experiments")
    public Collection<Experiment> getAllExperiments() {
        return consentService.getAllExperiments();
    }

    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewVisitor(@RequestParam String name,
                         @RequestParam String uname,
                         @RequestParam String pword) {
        Visitor v = new Visitor(0, uname, pword, name, new GregorianCalendar(1998, 05, 3));
        consentService.addNewUser(v);
        return "Saved\n";

    }
}
