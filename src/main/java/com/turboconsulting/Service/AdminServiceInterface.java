package com.turboconsulting.Service;

import com.turboconsulting.Entity.*;

import java.util.HashSet;

public interface AdminServiceInterface {
    boolean addNewAccount(Account a);
    Iterable<Account> getAllAccounts();

    boolean addNewVisitor(Visitor v, int accountID);
    Iterable<Visitor> getAllVisitors();

    boolean addNewExperiment(Experiment e, HashSet<ConsentOption> newConsentOptions);
    Iterable<Experiment> getAllExperiments();

    boolean doExperiment(int visitorId, int experimentId);
    Iterable<VisitorExperiment> getVisitorExperiments(int id);
    Iterable<VisitorExperiment>getAllVisitorExperiments();



}
