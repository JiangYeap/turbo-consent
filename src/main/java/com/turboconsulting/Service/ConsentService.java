package com.turboconsulting.Service;

import com.turboconsulting.DAO.*;
import com.turboconsulting.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ConsentService implements ConsentServiceInterface {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    @PostConstruct
    public void ConsentService() {

        accountDao.deleteAll();
        experimentDao.deleteAll();
        consentOptionDao.deleteAll();
        consentExperimentDao.deleteAll();

        consentOptionDao.save(new ConsentOption("NO CONSENT",
                "This option means you do not give consent for We the Curious to use any of your data"));
        consentOptionDao.save(new ConsentOption("FULL CONSENT",
                "This option means you give consent for We the Curious to use all of your data"));

        Account account1 = new Account("Harry", "hw16471@bristol.ac.uk", bCryptPasswordEncoder.encode("password"));
        addNewAccount(account1);
        Visitor visitor1 = new Visitor("Harry", new GregorianCalendar(0, 0, 0 ));
        addNewVisitor(visitor1, account1.getAccountId());
        Experiment experiment1 = new Experiment("Physics Experiment", "A lovely desciption.");
        addNewExperiment(experiment1, new HashSet<>());
        addVisitorExperiment(visitor1.getVisitorId(), experiment1.getId());
        updateExperimentConsent(visitor1.getVisitorId(), consentOptionDao.findByName("FULL CONSENT"), experiment1.getId());

        Account account2 = new Account("Finn", "user@turboconsent.com", bCryptPasswordEncoder.encode("password"));
        addNewAccount(account2);
        Visitor visitor2 = new Visitor("Finn", new GregorianCalendar(0, 0, 0 ));
        addNewVisitor(visitor2, account2.getAccountId());
        Experiment experiment2 = new Experiment("Chemistry Experiment", "A lovely desciption.");
        addNewExperiment(experiment2, new HashSet<>());
        addVisitorExperiment(visitor2.getVisitorId(), experiment1.getId());
        addVisitorExperiment(visitor2.getVisitorId(), experiment2.getId());

    }


    //////////////////////////////////////////////////////////////////////////ACCOUNT FUNCTIONS
    @Override
    public boolean addNewAccount(Account a)  {
        return (accountDao.findByEmail(a.getEmail()) == null) && (accountDao.save(a) != null);
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
    public boolean updateAccountConsent(List<Integer> vIds, ConsentOption c)  {
        for (int vId : vIds)  {
            updateVisitorConsent(vId, c);
        }
        return true;
    }


    //////////////////////////////////////////////////////////////////////////VISITOR FUNCTIONS
    @Override
    public boolean addNewVisitor(Visitor v, int accountID)  {
        v.setAccount(getAccount(accountID));
        v.setDefaultConsent(consentOptionDao.findByName("NO CONSENT"));
        consentOptionDao.findByName("NO CONSENT").addVisitor(v);
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
    public boolean updateVisitorConsent(int id, ConsentOption c)  {
        Visitor v = getVisitor(id);
        if(consentOptionDao.findByName(c.getName()) == null)  return false;
        if(!c.getName().equals("NO CONSENT") && !c.getName().equals("FULL CONSENT"))  return false;
        v.setDefaultConsent(consentOptionDao.findByName(c.getName()));
        return visitorDao.save(v) != null;
    }


    //////////////////////////////////////////////////////////////////////////EXPERIMENT FUNCTIONS
    @Override
    public boolean addNewExperiment(Experiment e, HashSet<ConsentOption> newConsentOptions){
        if( experimentDao.findByName(e.getName()) != null  )  return false;

        Set<ConsentExperiment> consentExperiments = new HashSet<>();
        consentExperiments.add(new ConsentExperiment(consentOptionDao.findByName("NO CONSENT"), e));
        consentExperiments.add(new ConsentExperiment(consentOptionDao.findByName("FULL CONSENT"), e));
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
    public Experiment getExperiment(int id)  {
        return experimentDao.findById(id);
    }
    @Override
    public Iterable<Experiment> getAllExperiments(){
        return experimentDao.findAll();
    }
    @Override
    public Iterable<ConsentOption> getExperimentsConsentOptions(int eId)  {
        ArrayList<ConsentOption> consentOptions = new ArrayList<>();
        Experiment experiment = experimentDao.findById(eId);
        for (ConsentExperiment consentExperiment : experiment.getConsentExperiments())  {
            consentOptions.add(consentExperiment.getConsentOption());
        }
        return consentOptions;

    }


    //////////////////////////////////////////////////////////////////////////VISITOR_EXPERIMENT FUNCTIONS
    @Override
    public ArrayList<VisitorExperiment> getVisitorExperiments(int id)  {
        ArrayList<VisitorExperiment> visitorExperimentsList = new ArrayList<>();
        Iterable<VisitorExperiment> visitorExperimentsIterable = visitorExperimentDao.findAllByVisitor(visitorDao.findByVisitorId(id));

        visitorExperimentsIterable.forEach(visitorExperimentsList::add);
        visitorExperimentsList.sort((ve1, ve2) -> ve2.getDate().compareTo(ve1.getDate()));

        return visitorExperimentsList;
    }
    @Override
    public VisitorExperiment getVisitorExperiment(int visitorID, int experimentID)  {
        ArrayList<Experiment> experiments = new ArrayList<>();
        Visitor v = visitorDao.findByVisitorId(visitorID);
        Experiment e = experimentDao.findById(experimentID);
        return visitorExperimentDao.findByVisitorAndExperiment(v, e);
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
    public String getExperimentConsent(int visitorID, int experimentID)  {
        Visitor v = visitorDao.findByVisitorId(visitorID);
        Experiment e = experimentDao.findById(experimentID);
        VisitorExperiment visitorExperiment = visitorExperimentDao.findByVisitorAndExperiment(v, e);
        return visitorExperiment == null ? "NULL" : visitorExperiment.getConsentOption().getName();
    }
    @Override
    public boolean updateExperimentConsent(int visitorId, ConsentOption newConsentOption, int experimentID)  {
        Visitor v = visitorDao.findByVisitorId(visitorId);
        Experiment e = experimentDao.findById(experimentID);
        if ( v == null || e == null )  return false;

        VisitorExperiment visitorExperiment = visitorExperimentDao.findByVisitorAndExperiment(v, e);
        for(ConsentExperiment c : e.getConsentExperiments())  {
            if(c.getConsentOption().getName().equals(newConsentOption.getName()))
                visitorExperiment.setConsentOption(c.getConsentOption());
        }
        return visitorExperimentDao.save(visitorExperiment) != null;
    }
    @Override
    public boolean updateBatchExperimentConsents(int visitorId, ConsentOption c, List<Integer> experimentIds)  {
        boolean batchSuccess = true;
        for (int eID : experimentIds)  batchSuccess = updateExperimentConsent(visitorId, c, eID) && batchSuccess;
        return batchSuccess;
    }
    @Override
    public int getPendingExperiments(int visitorId)  {
        return visitorDao.findByVisitorId(visitorId).getPendingExperiments();

    }
    @Override
    public boolean moveAllToReviewed(int visitorId)  {
        Iterable<VisitorExperiment> visitorExperiments = visitorExperimentDao.findAllByVisitor(getVisitor(visitorId));
        for(VisitorExperiment ve : visitorExperiments)  {
            ve.setChangedConsent(true);
            visitorExperimentDao.save(ve);
        }
        return true;
    }

}
