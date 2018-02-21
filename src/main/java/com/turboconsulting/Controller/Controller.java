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
    public @ResponseBody Iterable<Experiment> getAllExperiments() {
        return consentService.getAllExperiments();
    }

    @GetMapping(path = "/addVisitor")
    public @ResponseBody String addNewVisitor(@RequestParam String name,
                                              @RequestParam String uname,
                                              @RequestParam String pword) {
        Visitor v = new Visitor(uname, pword, name, new GregorianCalendar(1998, 05, 3));
        consentService.addNewUser(v);
        return "Saved Visitor\n";
    }


    @GetMapping(path = "/doExperiment")
    public @ResponseBody String doExperiment(@RequestParam int visitorId,
                                             @RequestParam int experimentId) {
        consentService.doExperiment(visitorId, experimentId);
        return "Visitor had done experiment\n";
    }

    @GetMapping(path = "/addExperiment")
    public @ResponseBody String addNewExperiment(@RequestParam String name) {
        Experiment e = new Experiment(name, "Sample Desciption");
        consentService.addNewExperiment(e);
        return "Saved Experiment\n";
    }
}
