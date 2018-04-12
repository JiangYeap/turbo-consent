package com.turboconsulting.Service;

import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Entity.VisitorExperiment;

public interface AdminServiceInterface {
    boolean addNewAccount(Account a);
    Iterable<Account> getAllAccounts();

    boolean addNewVisitor(Visitor v, int accountID);
    Iterable<Visitor> getAllVisitors();

    boolean addNewExperiment(Experiment e);
    Iterable<Experiment> getAllExperiments();

    boolean doExperiment(int visitorId, int experimentId);
    Iterable<VisitorExperiment> getVisitorExperiments(int id);
    Iterable<VisitorExperiment>getAllVisitorExperiments();

}
