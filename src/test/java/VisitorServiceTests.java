import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Service.ConsentService;
import com.turboconsulting.Service.ConsentServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
public class VisitorServiceTests {

    @Autowired
    private ConsentService consentService;

    @MockBean
    private AccountDao accountDao;

    @MockBean
    private ExperimentDao experimentDao;
    @MockBean
    private VisitorDao visitorDao;


    @TestConfiguration
    static class ConsentServiceImplTestContextConfiguration {

        @Bean
        public ConsentServiceInterface consentService(){
            return new ConsentService();
        }

    }

    @Before
    public void setup() {
        MockEntityFactory mockEntityFactory = new MockEntityFactory();
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Visitor> visitors = new ArrayList<>();

        accounts.add(mockEntityFactory.mockAccount(accountDao, "Harry", "harry@bristol.ac.uk", "password",1));
        accounts.add(mockEntityFactory.mockAccount(accountDao, "Finn", "finn@bristol.ac.uk", "password", 2));
        Mockito.when(accountDao.save(any(Account.class))).thenAnswer(AdditionalAnswers.<Account>returnsFirstArg());
        Mockito.when(accountDao.findAll()).thenReturn(accounts);

        visitors.add(mockEntityFactory.mockVisitor(visitorDao, "Harry Visitor 1", 1, accounts.get(0)));
        visitors.add(mockEntityFactory.mockVisitor(visitorDao, "Harry Visitor 2", 2, accounts.get(0)));
        Mockito.when(visitorDao.save(any(Visitor.class))).thenAnswer(AdditionalAnswers.<Visitor>returnsFirstArg());
        Mockito.when(visitorDao.findAll()).thenReturn(visitors);

    }

    @Test
    public void addNewVisitor_success()  {
        Visitor visitor = new Visitor( "Tony", new GregorianCalendar(2000, 01, 01), ConsentLevel.RESTRICTED);
        assertTrue(consentService.addNewVisitor(visitor, 1));
    }

    @Test
    public void getVisitor_withValidId() {
        Visitor found = consentService.getVisitor(1);
        assertEquals(found.getName(), "Harry Visitor 1");
        found = consentService.getVisitor(2);
        assertEquals(found.getName(), "Harry Visitor 2");

    }
    @Test
    public void getVisitor_withInvalidId() {
        assertEquals(consentService.getVisitor(-1), null);
        assertEquals(consentService.getVisitor(0), null);
        assertEquals(consentService.getVisitor(1000), null);

    }

    @Test
    public void updateVisitorConsent_validConsentLevel()  {
        Visitor found = consentService.getVisitor(1);
        assertEquals(found.getDefaultConsent(), ConsentLevel.RESTRICTED);
        assertTrue(consentService.updateVisitorConsent(found.getVisitorId(), ConsentLevel.NONE));
    }


}
