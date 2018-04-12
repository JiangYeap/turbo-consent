package com.turboconsulting.Service;

import com.turboconsulting.DAO.*;
import com.turboconsulting.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class AdminService implements AdminServiceInterface {
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
    @Autowired
    @Qualifier("sqlConsentData")
    private ConsentOptionDao consentOptionDao;
    @Autowired
    @Qualifier("sqlConsentExperimentData")
    private ConsentExperimentDao consentExperimentDao;


    @Override
    public boolean addNewAccount(Account a)  {
        return (accountDao.findByEmail(a.getEmail()) == null) && (accountDao.save(a) != null);
    }
    @Override
    public Iterable<Account> getAllAccounts(){
        return accountDao.findAll();
    }

    @Override
    public boolean addNewVisitor(Visitor v, int accountID)  {
        v.setAccount(accountDao.findByAccountId(accountID));
        v.setDefaultConsent(consentOptionDao.findByName("NO CONSENT"));
        consentOptionDao.findByName("NO CONSENT").addVisitor(v);
        return visitorDao.save(v) != null;
    }
    @Override
    public Iterable<Visitor> getAllVisitors(){
        return visitorDao.findAll();
    }

    @Override
    public boolean addNewExperiment(Experiment e, HashSet<ConsentOption> newConsentOptions){
        if( experimentDao.findByName(e.getName()) != null  )  return false;

        Set<ConsentExperiment> consentExperiments = new HashSet<>();
        consentExperiments.add(new ConsentExperiment(consentOptionDao.findByName("FULL CONSENT"), e));
        consentExperiments.add(new ConsentExperiment(consentOptionDao.findByName("NO CONSENT"), e));

        for (ConsentOption c : newConsentOptions)  {
            if (consentOptionDao.findByName(c.getName()) == null)  {
                consentOptionDao.save(c);
            }
            consentExperiments.add(new ConsentExperiment(consentOptionDao.findByName(c.getName()), e));
        }
        e.setConsentExperiments(consentExperiments);
        experimentDao.save(e);
        for(ConsentExperiment consentExperiment : consentExperiments)  {
            consentExperiment.getConsentOption().addConsentExperiment(consentExperiment);
            consentOptionDao.save(consentExperiment.getConsentOption());
        }
        return true;
    }
    @Override
    public Iterable<Experiment> getAllExperiments(){
        return experimentDao.findAll();
    }

    @Override
    public ArrayList<VisitorExperiment> getVisitorExperiments(int id)  {
        ArrayList<VisitorExperiment> visitorExperimentsList = new ArrayList<>();
        Iterable<VisitorExperiment> visitorExperimentsIterable = visitorExperimentDao.findAllByVisitor(visitorDao.findByVisitorId(id));

        visitorExperimentsIterable.forEach(visitorExperimentsList::add);
        visitorExperimentsList.sort((ve1, ve2) -> ve2.getDate().compareTo(ve1.getDate()));

        return visitorExperimentsList;
    }
    @Override
    public boolean addVisitorExperiment(int visitorId, int experimentId)  {
        VisitorExperiment visitorExperiment = new VisitorExperiment( visitorDao.findByVisitorId(visitorId),
                experimentDao.findById(experimentId));

        experimentDao.findById(experimentId).addVisitorExperiment(visitorExperiment);
        Visitor v = visitorDao.findByVisitorId(visitorId);
        v.doExperiment(visitorExperiment);
        visitorExperiment.getConsentOption().addExperiment(visitorExperiment);
        //consentOptionDao.save(visitorExperiment.getConsentOption());
        return visitorDao.save(v) != null;
    }
    @Override
    public Iterable<VisitorExperiment> getAllVisitorExperiments()  {
        return visitorExperimentDao.findAll();
    }



}
