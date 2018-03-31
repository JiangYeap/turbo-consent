import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Visitor;
import org.mockito.Mockito;

import java.util.GregorianCalendar;

public class MockEntityFactory {

    public Account mockAccount(AccountDao accountDao, String name, String email, String password, int id)  {
        Account newAccount = new Account(name, email, password);
        newAccount.setAccountId(id);
        Mockito.when(accountDao.findByAccountId(newAccount.getAccountId())).thenReturn(newAccount);
        Mockito.when(accountDao.findByEmail(newAccount.getEmail())).thenReturn(newAccount);
        return newAccount;
    }

    public Visitor mockVisitor(VisitorDao visitorDao, String name, int id, Account account)  {
        Visitor newVisitor = new Visitor( name, new GregorianCalendar(2000, 01, 01), ConsentLevel.RESTRICTED);
        newVisitor.setVisitorId(id);
        newVisitor.setAccount(account);
        Mockito.when(visitorDao.findByVisitorId(newVisitor.getVisitorId())).thenReturn(newVisitor);
        return newVisitor;
    }

}
