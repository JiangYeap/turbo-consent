package com.turboconsulting.Controller;

import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping(path = "/accounts")
    public @ResponseBody Iterable<Account> getAllAccounts() {
        return adminService.getAllAccounts();
    }

    @GetMapping(path = "/visitors")
    public @ResponseBody Iterable<Visitor> getAllVisitors() {
        return adminService.getAllVisitors();
    }

    @GetMapping(path = "/experiments")
    public @ResponseBody Iterable<Experiment> getAllExperiments() {
        return adminService.getAllExperiments();
    }

    @GetMapping(path = "/addAccount")
    public @ResponseBody String addNewAccount(@RequestParam String name,
                                              @RequestParam String email) {
        Account a = new Account(name, email, bCryptPasswordEncoder.encode("password"));
        adminService.addNewAccount(a);
        return "Saved Account\n";
    }

    @GetMapping(path = "/addVisitor")
    public @ResponseBody String addNewVisitor(@RequestParam String name,
                                              @RequestParam int accountID) {
        Visitor v = new Visitor( name, new GregorianCalendar(1998, 05, 3));
        adminService.addNewVisitor(v, accountID);
        return "Saved Visitor\n";
    }

    @GetMapping(path = "/addExperiment")
    public @ResponseBody String addNewExperiment(@RequestParam String name) {
//        Experiment e = new Experiment(name, "Sample Desciption", new HashSet<>());
//        adminService.addNewExperiment(e);
//        return "Saved Experiment\n";
        return "Saved Experiment\n";
    }


    @GetMapping(path = "/doExperiment")
    public @ResponseBody String doExperiment(@RequestParam int visitorId,
                                             @RequestParam int experimentId) {
        adminService.doExperiment(visitorId, experimentId);
        return "Visitor had done experiment\n";
    }

}
