package com.turboconsulting.DAO;

import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Entity.Visitor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.util.Collection;

@Repository
@Qualifier("sqlVisitorData")
public interface VisitorDao extends CrudRepository<Visitor, Integer> {

    Visitor findByVisitorId(int id);

    Iterable<Visitor> findAllByAccount(Account account);

}
