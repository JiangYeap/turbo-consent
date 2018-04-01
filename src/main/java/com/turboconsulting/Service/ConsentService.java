package com.turboconsulting.Service;

import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.DAO.VisitorExperimentDao;
import com.turboconsulting.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

@Service
public class ConsentService implements ConsentServiceInterface {

    @Autowired
    @Qualifier("sqlVisitorData")
    private VisitorDao visitorDao;

    @Autowired
    @Qualifier("sqlVisitorExperimentData")
    private VisitorExperimentDao visitorExperimentDao;

    @Autowired
    @Qualifier("sqlAccountData")
    private AccountDao accountDao;

    @Autowired
    @Qualifier("sqlExperimentData")
    private ExperimentDao experimentDao;


    @Override
    @PostConstruct
    public void ConsentService() {

//        accountDao.deleteAll();
//        experimentDao.deleteAll();
//        Account newAccount = new Account("Harry", "hw16471@bristol.ac.uk", "password");
//        addNewAccount(newAccount);
//
//        Visitor newVisitor = new Visitor("Harry", new GregorianCalendar(0, 0, 0 ), ConsentLevel.RESTRICTED);
//        addNewVisitor(newVisitor, newAccount.getAccountId());
//        Experiment newExperiment = new Experiment("Physics Experiment", "A lovely desciption.");
//        addNewExperiment(newExperiment);
//        doExperiment(newVisitor.getVisitorId(), newExperiment.getId());

    }


    //////////////////////////////////////////////////////////////////////////ACCOUNT FUNCTIONS
    @Override
    public boolean addNewAccount(Account a)  {
        return accountDao.save(a) != null;

    }
    @Override
    public int getAccountID(String email)  {
        if(accountDao.findByEmail(email) != null)
            return accountDao.findByEmail(email).getAccountId();
        return -1;
    }
    @Override
    public boolean checkAccountLogin(LoginDetails loginDetails)  {
        Account a = accountDao.findByEmail(loginDetails.getEmail());
        return a != null && a.getPassword().equals(loginDetails.getPword());
    }
    @Override
    public Iterable<Visitor> getAccountsVisitors(int aID) {
        return visitorDao.findAllByAccount(accountDao.findOne(aID));
    }
    @Override
    public Iterable<Account> getAllAccounts(){
        return accountDao.findAll();
    }
    @Override
    public Account getAccount(int id)  {
        return accountDao.findByAccountId(id);
    }
    @Override
    public boolean updateAccountConsent(int id, ConsentLevel c)  {
        Account a = accountDao.findByAccountId(id);
        a.setConsentLevel(c);
        return accountDao.save(a) != null;
    }


    //////////////////////////////////////////////////////////////////////////VISITOR FUNCTIONS
    @Override
    public boolean addNewVisitor(Visitor v, int accountID)  {
        v.setAccount(getAccount(accountID));
        return visitorDao.save(v) != null;
    }
    @Override
    public Visitor getVisitor(int id)  {
        return visitorDao.findByVisitorId(id);
    }
    @Override
    public Iterable<Visitor> getAllVisitors(){
        return visitorDao.findAll();
    }
    @Override
    public boolean updateVisitorConsent(int id, ConsentLevel c)  {
        Visitor v = getVisitor(id);
        v.setDefaultConsent(c);
        return visitorDao.save(v) != null;
    }


    //////////////////////////////////////////////////////////////////////////EXPERIMENT FUNCTIONS
    @Override
    public boolean addNewExperiment(Experiment e){
        return experimentDao.save(e) != null;
    }
    @Override
    public Experiment getExperiment(int id)  {
        return experimentDao.findById(id);
    }
    @Override
    public Iterable<Experiment> getAllExperiments(){
        return experimentDao.findAll();
    }



    //////////////////////////////////////////////////////////////////////////VISITOR_EXPERIMENT FUNCTIONS
    @Override
    public Iterable<Experiment> getVisitorExperiments(int id)  {
        Collection<Experiment> experiments = new ArrayList<>();

        Visitor v = visitorDao.findByVisitorId(id);
        for (VisitorExperiment e : v.getExperiments())  {
            experiments.add(e.getExperiment());
        }
        return experiments;
    }
    @Override
    public boolean doExperiment(int visitorId, int experimentId)  {
        VisitorExperiment e = new VisitorExperiment( visitorDao.findByVisitorId(visitorId),
                                                     experimentDao.findById(experimentId),
                                                     visitorDao.findByVisitorId(visitorId).getDefaultConsent());

        experimentDao.findById(experimentId).doExperiment(e);
        Visitor v = visitorDao.findByVisitorId(visitorId);
        v.doExperiment(e);
        return visitorDao.save(v) != null;
    }
    @Override
    public String getExperimentConsent(int id, int experimentID)  {
        Visitor v = visitorDao.findByVisitorId(id);
        for (VisitorExperiment ve : v.getExperiments())  {
            if (ve.getExperiment().getId() == experimentID)  return ve.getConsentLevel().toString();
        }
        return "NULL";
    }
    @Override
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
}
