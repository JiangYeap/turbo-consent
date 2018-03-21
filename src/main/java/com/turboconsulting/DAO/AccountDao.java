package com.turboconsulting.DAO;

import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.Visitor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("sqlAccountData")
public interface AccountDao extends CrudRepository<Account, Integer> {

}
