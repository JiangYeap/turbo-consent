package com.turboconsulting.Service;

import com.turboconsulting.DAO.*;
import com.turboconsulting.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

@Service
public class AdminService implements AdminServiceInterface {


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
    public void AdminService() {

        accountDao.deleteAll();
        experimentDao.deleteAll();
        consentOptionDao.deleteAll();
        consentExperimentDao.deleteAll();

        consentOptionDao.save(new ConsentOption("FULL CONSENT",
                "This option means you give consent for We The Curious and the involved researched to use all of the data collected when you participated in this experiment."));
        consentOptionDao.save(new ConsentOption("NO CONSENT",
                "This option means you do not give consent for We The Curious or any other parties to use any of the data collected when you participated in this experiment."));

        Account account1 = new Account("Admin", "admin@turboconsent.com", bCryptPasswordEncoder.encode("tcadmin123"));
        addNewAccount(account1);

        Account account2 = new Account("Harry", "hw16471@bristol.ac.uk", bCryptPasswordEncoder.encode("password"));
        addNewAccount(account2);
        Visitor visitor1 = new Visitor("Harry", new GregorianCalendar(0, 0, 0 ));
        addNewVisitor(visitor1, account2.getAccountId());
        Experiment experiment1 = new Experiment("Physics Experiment", "A lovely desciption.");
        addNewExperiment(experiment1, new HashSet<>());
        addVisitorExperiment(visitor1.getVisitorId(), experiment1.getId());

        Account account3 = new Account("Finn", "user@turboconsent.com", bCryptPasswordEncoder.encode("password"));
        addNewAccount(account3);
        Visitor visitor2 = new Visitor("Finn", new GregorianCalendar(0, 0, 0 ));
        addNewVisitor(visitor2, account3.getAccountId());
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
    public Iterable<Account> getAllAccounts(){
        return accountDao.findAll();
    }
    @Override
    public boolean deleteAccount(int accountId) {
        boolean successful = true;
        for(Visitor visitor : accountDao.findByAccountId(accountId).getVisitors())  {
            successful = successful & deleteVisitor(visitor.getVisitorId());
        }
        accountDao.delete(accountId);
        return successful;
    }

    //////////////////////////////////////////////////////////////////////////VISITOR FUNCTIONS
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
    public boolean deleteVisitor(int visitorId) {
        boolean successful = true;
        for (VisitorExperiment visitorExperiment : visitorDao.findByVisitorId(visitorId).getExperiments())  {
            successful = successful & deleteVisitorExperiment(visitorExperiment.getCompoundKey());
        }
        visitorDao.delete(visitorId);
        return successful;
    }

    //////////////////////////////////////////////////////////////////////////EXPERIMENT FUNCTIONS
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
    public boolean deleteExperiment(int experimentId) {
        for(VisitorExperiment visitorExperiment: experimentDao.findById(experimentId).getVisitors())  {
            visitorExperimentDao.delete(visitorExperiment.getCompoundKey());
        }
        for(ConsentExperiment consentExperiment : experimentDao.findById(experimentId).getConsentExperiments())  {
            consentExperimentDao.delete(consentExperiment.getCompoundKey());
        }
        experimentDao.delete(experimentId);
        return true;
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
    @Override
    public boolean deleteVisitorExperiment(int visitorExperimentId) {
        visitorExperimentDao.delete(visitorExperimentId);
        return true;
    }
}
