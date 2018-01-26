package com.turboconsulting.Service;

import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ConsentService {

    @Autowired
    @Qualifier("visitorData")
    private VisitorDao visitorDao;

    @Autowired
    @Qualifier("experimentData")
    private ExperimentDao experimentDao;

    public Collection<Visitor> getAllVisitors(){
        return visitorDao.getAllVisitors();
    }

    public Collection<Experiment> getAllExperiments(){
        return experimentDao.getAllExperiments();
    }
}