package com.turboconsulting.Service;

import com.turboconsulting.Entity.*;

import javax.annotation.PostConstruct;

public interface ConsentServiceInterface {
    @PostConstruct
    void ConsentService();

    //////////////////////////////////////////////////////////////////////////ACCOUNT FUNCTIONS
    void addNewAccount(Account a);

    int getAccountID(String email);

    boolean checkAccountLogin(LoginDetails loginDetails);

    Iterable<Visitor> getAccountsVisitors(int aID);

    Iterable<Account> getAllAccounts();

    Account getAccount(int id);

    void updateAccountConsent(int id, ConsentLevel c);

    //////////////////////////////////////////////////////////////////////////VISITOR FUNCTIONS
    void addNewVisitor(Visitor v, int accountID);

    Visitor getVisitor(int id);

    Iterable<Visitor> getAllVisitors();

    void updateVisitorConsent(int id, ConsentLevel c);

    //////////////////////////////////////////////////////////////////////////EXPERIMENT FUNCTIONS
    void addNewExperiment(Experiment e);

    Experiment getExperiment(int id);

    Iterable<Experiment> getAllExperiments();

    void updateExperimentConsent(int visitorId, ConsentLevel c, int experimentID);

    //////////////////////////////////////////////////////////////////////////VISITOR_EXPERIMENT FUNCTIONS
    Iterable<Experiment> getVisitorExperiments(int id);

    void doExperiment(int visitorId, int experimentId);

    String getExperimentConsent(int id, int experimentID);
}