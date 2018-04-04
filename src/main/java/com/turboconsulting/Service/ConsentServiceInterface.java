package com.turboconsulting.Service;

import com.turboconsulting.Entity.*;

import javax.annotation.PostConstruct;

public interface ConsentServiceInterface {
    @PostConstruct
    void ConsentService();

    //////////////////////////////////////////////////////////////////////////ACCOUNT FUNCTIONS
    boolean addNewAccount(Account a);
    int getAccountID(String email);
    boolean checkAccountLogin(LoginDetails loginDetails);
    Iterable<Visitor> getAccountsVisitors(int aID);
    Iterable<Account> getAllAccounts();
    Account getAccount(int id);
    boolean updateAccountConsent(int id, ConsentLevel c);

    //////////////////////////////////////////////////////////////////////////VISITOR FUNCTIONS
    boolean addNewVisitor(Visitor v, int accountID);
    Visitor getVisitor(int id);
    Iterable<Visitor> getAllVisitors();
    boolean updateVisitorConsent(int id, ConsentLevel c);

    //////////////////////////////////////////////////////////////////////////EXPERIMENT FUNCTIONS
    boolean addNewExperiment(Experiment e);
    Experiment getExperiment(int id);
    Iterable<Experiment> getAllExperiments();

    //////////////////////////////////////////////////////////////////////////VISITOR_EXPERIMENT FUNCTIONS
    Iterable<VisitorExperiment> getVisitorExperiments(int id);
    VisitorExperiment getVisitorExperiment(int visitorID, int experimentID);
    boolean doExperiment(int visitorId, int experimentId);
    String getExperimentConsent(int id, int experimentID);
    boolean updateExperimentConsent(int visitorId, ConsentLevel c, int experimentID);
    int getPendingExperiments(int id);
}
