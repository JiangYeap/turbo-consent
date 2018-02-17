package com.turboconsulting.Service;

import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.MySqlVisitorDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Entity.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ConsentService {

    @Autowired
    @Qualifier("sqlVisitorData")
    private MySqlVisitorDao visitorDao;

    @Autowired
    @Qualifier("experimentData")
    private ExperimentDao experimentDao;

    public Iterable<Visitor> getAllVisitors(){
        return visitorDao.findAll();
    }

    public Collection<Experiment> getAllExperiments(){
        return experimentDao.getAllExperiments();
    }

    public boolean checkLoginDetails(LoginDetails loginDetails)  {
        Iterable<Visitor> visitors = visitorDao.findAll();
        for (Visitor v : visitors) {
            if(v.getUname().equals(loginDetails.uname))  {
                if(v.getPassword().equals(loginDetails.pword))  return true;
            }
        }
        return false;
    }

    public void updatePassword(LoginDetails login) {
        //visitorDao.updateVisitor(login);


    }

    public void addNewUser(Visitor v)  {
        //visitorDao.addNewVisitor(v);
        visitorDao.save(v);

    }
}
