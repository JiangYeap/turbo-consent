package com.turboconsulting.Service;

import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ConsentService {

    @Autowired
    @Qualifier("sqlVisitorData")
    private VisitorDao visitorDao;

    @Autowired
    @Qualifier("sqlAccountData")
    private AccountDao accountDao;

    @Autowired
    @Qualifier("sqlExperimentData")
    private ExperimentDao experimentDao;

    //////////////////////////////////////////////////////////////////////////ACCOUNT FUNCTIONS
    public void addNewAccount(Account a)  {
        accountDao.save(a);
    }
    public int getAccountID(String email)  {
        for (Account a : accountDao.findAll())  {
            if (a.getEmail().equals(email))  return a.getAccountId();
        }
        return -1;
    }
    public boolean checkAccountLogin(LoginDetails loginDetails)  {
        Iterable<Account> visitors = accountDao.findAll();
        for (Account v : visitors) {
            if(v.getEmail().equals(loginDetails.getEmail()))  {
                if(v.getPassword().equals(loginDetails.getPword()))  return true;
            }
        }
        return false;
    }
    public Iterable<Account> getAllAccounts(){
        return accountDao.findAll();
    }


    //////////////////////////////////////////////////////////////////////////VISITOR FUNCTIONS
    public void addNewVisitor(Visitor v, int accountID)  {
        v.setAccount(accountDao.findOne(accountID));
        visitorDao.save(v);
    }
    public Visitor getVisitor(int id)  {
        return visitorDao.findOne(id);
    }
    public Iterable<Visitor> getAllVisitors(){
        return visitorDao.findAll();
    }
    public void updateVisitorConsent(int id, ConsentLevel c)  {
        Visitor v = visitorDao.findOne(id);
        v.setDefaultConsent(c);
        visitorDao.save(v);
    }


    //////////////////////////////////////////////////////////////////////////EXPERIMENT FUNCTIONS
    public void addNewExperiment(Experiment e){
        experimentDao.save(e);
    }
    public Experiment getExperiment(int id)  {
        return experimentDao.findOne(id);
    }
    public Iterable<Experiment> getAllExperiments(){
        return experimentDao.findAll();
    }
    public void updateExperimentConsent(int visitorId, ConsentLevel c, int experimentID)  {
        Visitor v = visitorDao.findOne(visitorId);
        for (VisitorExperiment ve : v.getExperiments())  {
            if (ve.getExperiment().getId() == experimentID)  {
                ve.setConsentLevel(c);
                v.doExperiment(ve);
            }
        }
        visitorDao.save(v);
    }


    //////////////////////////////////////////////////////////////////////////VISITOR_EXPERIMENT FUNCTIONS
    public Iterable<Experiment> getVisitorExperiments(int id)  {
        Collection<Experiment> experiments = new ArrayList<>();

        Visitor v = visitorDao.findOne(id);
        for (VisitorExperiment e :v.getExperiments())  {
            experiments.add(e.getExperiment());
        }
        return experiments;
    }
    public void doExperiment(int visitorId, int experimentId)  {
        VisitorExperiment e = new VisitorExperiment(visitorDao.findOne(visitorId), experimentDao.findOne(experimentId), ConsentLevel.RESTRICTED);
        experimentDao.findOne(experimentId).doExperiment(e);
        Visitor v = visitorDao.findOne(visitorId);
        v.doExperiment(e);
        visitorDao.save(v);
    }
    public String getExperimentConsent(int id, int experimentID)  {
        Visitor v = visitorDao.findOne(id);
        for (VisitorExperiment ve : v.getExperiments())  {
            if (ve.getExperiment().getId() == experimentID)  return ve.getConsentLevel().toString();
        }
        return "NULL";
    }
}
