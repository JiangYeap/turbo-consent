package com.turboconsulting.Service;

import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Entity.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ConsentService {

    @Autowired
    @Qualifier("fakeVisitorData")
    private VisitorDao fakeVisitorDaoImpl;

    @Autowired
    @Qualifier("experimentData")
    private ExperimentDao experimentDao;

    public Collection<Visitor> getAllVisitors(){
        return fakeVisitorDaoImpl.getAllVisitors();
    }

    public Collection<Experiment> getAllExperiments(){
        return experimentDao.getAllExperiments();
    }

    public boolean checkLoginDetails(LoginDetails loginDetails)  {
        Collection<Visitor> visitors = fakeVisitorDaoImpl.getAllVisitors();
        for (Visitor v : visitors) {
            if(v.getUname().equals(loginDetails.uname))  {
                if(v.getPassword().equals(loginDetails.pword))  return true;
            }
        }
        return false;
    }

    public void updatePassword(LoginDetails login) {
        fakeVisitorDaoImpl.updateVisitor(login);

    }
}
