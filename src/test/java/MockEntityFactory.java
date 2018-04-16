import com.turboconsulting.DAO.*;
import com.turboconsulting.Entity.*;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.*;

public class MockEntityFactory {

    public Account mockAccount(AccountDao accountDao, String name, String email, String password, int id)  {
        Account newAccount = new Account(name, email, password);
        newAccount.setAccountId(id);
        Mockito.when(accountDao.findByAccountId(newAccount.getAccountId())).thenReturn(newAccount);
        Mockito.when(accountDao.findByEmail(newAccount.getEmail())).thenReturn(newAccount);
        return newAccount;
    }

    public Visitor mockVisitor(VisitorDao visitorDao, String name, int id, Account account)  {
        Visitor newVisitor = new Visitor( name, new GregorianCalendar(2000, 01, 01));
        newVisitor.setVisitorId(id);
        newVisitor.setAccount(account);
        newVisitor.setDefaultConsent(new ConsentOption("NO CONSENT", "No consent given."));
        Mockito.when(visitorDao.findByVisitorId(newVisitor.getVisitorId())).thenReturn(newVisitor);
        Set<Visitor> visitors = account.getVisitors();
        visitors.add(newVisitor);
        account.setVisitors(visitors);
        return newVisitor;
    }

//    public Experiment mockExperiment(ExperimentDao experimentDao, String name, String description, int id)  {
//        Experiment newExperiment = new Experiment(name, description, new HashSet<>());
//        newExperiment.setId(id);
//        Mockito.when(experimentDao.findById(newExperiment.getId())).thenReturn(newExperiment);
//        return newExperiment;
//    }

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


    public ConsentOption mockConsentOption(String name, String description, ConsentOptionDao consentOptionDao, int id)  {
        ConsentOption newConsentOption = new ConsentOption(name, description);
        newConsentOption.setConsentId(id);
        Mockito.when(consentOptionDao.findByName(name)).thenReturn(newConsentOption);
        return newConsentOption;
    }
}
