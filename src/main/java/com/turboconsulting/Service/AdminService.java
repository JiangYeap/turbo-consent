package com.turboconsulting.Service;

import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.DAO.VisitorExperimentDao;
import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Entity.VisitorExperiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        return visitorDao.save(v) != null;
    }
    @Override
    public Iterable<Visitor> getAllVisitors(){
        return visitorDao.findAll();
    }

    @Override
    public boolean addNewExperiment(Experiment e){
        return (experimentDao.findByName(e.getName()) == null) && (experimentDao.save(e) != null);
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
    public boolean doExperiment(int visitorId, int experimentId)  {
        VisitorExperiment e = new VisitorExperiment( visitorDao.findByVisitorId(visitorId),
                experimentDao.findById(experimentId));

        experimentDao.findById(experimentId).addVisitorExperiment(e);
        Visitor v = visitorDao.findByVisitorId(visitorId);
        v.doExperiment(e);
        return visitorDao.save(v) != null;
    }



}
