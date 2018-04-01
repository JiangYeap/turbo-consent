import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.DAO.VisitorExperimentDao;
import com.turboconsulting.Entity.*;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

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

    public Experiment mockExperiment(ExperimentDao experimentDao, String name, String description, int id)  {
        Experiment newExperiment = new Experiment(name, description);
        newExperiment.setId(id);
        Mockito.when(experimentDao.findById(newExperiment.getId())).thenReturn(newExperiment);
        return newExperiment;
    }

    public VisitorExperiment mockVisitorExperiment(VisitorDao visitorDao, ExperimentDao experimentDao, VisitorExperimentDao visitorExperimentDao, int visitorId, int experimentId)  {
        VisitorExperiment visitorExperiment = new VisitorExperiment(
                visitorDao.findByVisitorId(visitorId),
                experimentDao.findById(experimentId));
        Visitor v = visitorDao.findByVisitorId(visitorId);
        Experiment e = experimentDao.findById(experimentId);
        v.addExperiment(visitorExperiment);
        Mockito.when(visitorExperimentDao.findAllByVisitor(v)).thenReturn(v.getExperiments());
        Mockito.when(visitorExperimentDao.findByVisitorAndExperiment(v, e)).thenReturn(visitorExperiment);
        return visitorExperiment;
    }

}
