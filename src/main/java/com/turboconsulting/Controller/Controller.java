package com.turboconsulting.Controller;

import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/data")
public class Controller {

    @Autowired
    private ConsentService consentService;


    @RequestMapping(value = "/visitors", method = RequestMethod.GET)
    public Collection<Visitor> getAllStudents() {
        return consentService.getAllVisitors();
    }

    @RequestMapping(value = "/experiments", method = RequestMethod.GET)
    public Collection<Experiment> getAllExperiments(){
        return consentService.getAllExperiments();
    }


}
