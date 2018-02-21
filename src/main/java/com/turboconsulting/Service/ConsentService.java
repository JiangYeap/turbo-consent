package com.turboconsulting.Service;

import com.turboconsulting.DAO.MySqlExperimentDao;
import com.turboconsulting.DAO.MySqlVisitorDao;
import com.turboconsulting.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConsentService {

    @Autowired
    @Qualifier("sqlVisitorData")
    private MySqlVisitorDao visitorDao;

    @Autowired
    @Qualifier("sqlExperimentData")
    private MySqlExperimentDao experimentDao;

    public Iterable<Visitor> getAllVisitors(){
        return visitorDao.findAll();
    }

    public Iterable<Experiment> getAllExperiments(){
        return experimentDao.findAll();
    }

    public boolean checkLoginDetails(LoginDetails loginDetails)  {
        Iterable<Visitor> visitors = visitorDao.findAll();
        for (Visitor v : visitors) {
            if(v.getUname().equals(loginDetails.getUname()))  {
                if(v.getPassword().equals(loginDetails.getPword()))  return true;
            }
        }
        return false;
    }

    public void updatePassword(LoginDetails login) {
        //visitorDao.updateVisitor(login);


    }

    public void doExperiment(int visitorId, int experimentId)  {
        VisitorExperiment e = new VisitorExperiment(visitorDao.findOne(visitorId), experimentDao.findOne(experimentId), ConsentLevel.NONE);
        experimentDao.findOne(experimentId).doExperiment(e);
        Visitor v = visitorDao.findOne(visitorId);
        v.doExperiment(e);
        visitorDao.save(v);
    }

    public void addNewUser(Visitor v)  {
        visitorDao.save(v);
    }

    public void addNewExperiment(Experiment e){
        experimentDao.save(e);
    }
}
