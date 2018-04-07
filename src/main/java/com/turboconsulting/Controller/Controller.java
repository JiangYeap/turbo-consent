package com.turboconsulting.Controller;

import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;

@RestController
@RequestMapping("/data")
public class Controller {

    @Autowired
    private ConsentService consentService;


    @GetMapping(path = "/accounts")
    public @ResponseBody Iterable<Account> getAllAccounts() {
        return consentService.getAllAccounts();
    }

    @GetMapping(path = "/visitors")
    public @ResponseBody Iterable<Visitor> getAllVisitors() {
        return consentService.getAllVisitors();
    }

    @GetMapping(path = "/experiments")
    public @ResponseBody Iterable<Experiment> getAllExperiments() {
        return consentService.getAllExperiments();
    }

    @GetMapping(path = "/addAccount")
    public @ResponseBody String addNewAccount(@RequestParam String name,
                                              @RequestParam String email) {
        Account a = new Account(name, email, "password");
        consentService.addNewAccount(a);
        return "Saved Account\n";
    }

    @GetMapping(path = "/addVisitor")
    public @ResponseBody String addNewVisitor(@RequestParam String name,
                                              @RequestParam int accountID) {
        Visitor v = new Visitor( name, new GregorianCalendar(1998, 05, 3));
        consentService.addNewVisitor(v, accountID);
        return "Saved Visitor\n";
    }

    @GetMapping(path = "/addExperiment")
    public @ResponseBody String addNewExperiment(@RequestParam String name) {
        Experiment e = new Experiment(name, "Sample Desciption");
        consentService.addNewExperiment(e);
        return "Saved Experiment\n";
    }


    @GetMapping(path = "/doExperiment")
    public @ResponseBody String doExperiment(@RequestParam int visitorId,
                                             @RequestParam int experimentId) {
        consentService.doExperiment(visitorId, experimentId);
        return "Visitor had done experiment\n";
    }

}
