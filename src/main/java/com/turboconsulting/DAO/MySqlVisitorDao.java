package com.turboconsulting.DAO;

import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Entity.Visitor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.Collection;

@Repository
@Qualifier("sqlVisitorData")
public class MySqlVisitorDao implements VisitorDao{

    Connection mySqlConnection;

    @Override
    public Collection<Visitor> getAllVisitors() {
        return null;
    }

    @Override
    public void updateVisitor(LoginDetails newLogin) {

    }

    @Override
    public void addNewVisitor(Visitor v) {

    }
}
