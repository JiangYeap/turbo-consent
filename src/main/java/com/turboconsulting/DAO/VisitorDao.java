package com.turboconsulting.DAO;

import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Entity.Visitor;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface VisitorDao {
    Collection<Visitor> getAllVisitors();

    void updateVisitor(LoginDetails newLogin);

    void addNewVisitor(Visitor v);
}
