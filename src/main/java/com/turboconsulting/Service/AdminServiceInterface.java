package com.turboconsulting.Service;

import com.turboconsulting.Entity.*;

import javax.annotation.PostConstruct;
import java.util.HashSet;

public interface AdminServiceInterface {

    @PostConstruct
    void AdminService();

    boolean addNewAccount(Account a);
    Iterable<Account> getAllAccounts();

    boolean addNewVisitor(Visitor v, int accountID);
    Iterable<Visitor> getAllVisitors();

    boolean addNewExperiment(Experiment e, HashSet<ConsentOption> newConsentOptions);
    Iterable<Experiment> getAllExperiments();


    public boolean addVisitorExperiment(int visitorId, int experimentId);
    Iterable<VisitorExperiment> getVisitorExperiments(int id);
    Iterable<VisitorExperiment>getAllVisitorExperiments();



}
