package com.turboconsulting.Controller;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class Controller {

    @Autowired
    private ConsentService consentService;

    private static Collection<Visitor> visitors = new ArrayList<>();


    @GetMapping(path = "/visitors")
    public @ResponseBody Iterable<Visitor> getAllStudents() {
        return consentService.getAllVisitors();
    }

    @GetMapping(path = "/experiments")
    public Collection<Experiment> getAllExperiments() {
        return consentService.getAllExperiments();
    }

    @GetMapping(path = "/add")
    public @ResponseBody String addNewVisitor(@RequestParam String name,
                                              @RequestParam String uname,
                                              @RequestParam String pword) {
        Visitor v = new Visitor(0, uname, pword, name, new GregorianCalendar(1998, 05, 3));
        consentService.addNewUser(v);
        return "Saved\n";

    }
}
