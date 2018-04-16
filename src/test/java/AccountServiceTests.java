import com.turboconsulting.DAO.*;
import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.ConsentOption;
import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Service.ConsentService;
import com.turboconsulting.Service.ConsentServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;

@RunWith(SpringRunner.class)
public class AccountServiceTests {

    @Autowired
    private ConsentService consentService;
    @MockBean
    private AccountDao accountDao;
    @MockBean
    private ExperimentDao experimentDao;
    @MockBean
    private VisitorDao visitorDao;
    @MockBean
    private VisitorExperimentDao visitorExperimentDao;
    @MockBean
    private ConsentOptionDao consentOptionDao;
    @MockBean
    private ConsentExperimentDao consentExperimentDao;

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

        accounts.add(mockEntityFactory.mockAccount(accountDao, "Harry", "harry@bristol.ac.uk", "password", 1));
        accounts.add(mockEntityFactory.mockAccount(accountDao, "Finn", "finn@bristol.ac.uk", "password", 2));
        accounts.add(mockEntityFactory.mockAccount(accountDao, "Yeap", "yeap@bristol.ac.uk", "password", 3));
        //Mockito.when(accountDao.save(any(Account.class))).thenAnswer(AdditionalAnswers.<Account>returnsFirstArg());
        //Mockito.when(accountDao.findAll()).thenReturn(accounts);

        visitors.add(mockEntityFactory.mockVisitor(visitorDao, "Harry Visitor", 1, accounts.get(0)));
        visitors.add(mockEntityFactory.mockVisitor(visitorDao, "Harry Visitor", 2, accounts.get(0)));
        //Mockito.when(visitorDao.save(any(Visitor.class))).thenAnswer(AdditionalAnswers.<Visitor>returnsFirstArg());
        //Mockito.when(visitorDao.findAll()).thenReturn(visitors);

        Mockito.when(visitorDao.findAllByAccount(any(Account.class))).thenAnswer(new Answer<Set<Visitor>>() {
            @Override
            public Set<Visitor> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return ((Account)args[0]).getVisitors();
            }
        });

    }

    @Test
    public void getAccount_withValidId() {
        Account found = consentService.getAccount(1);
        assertEquals(found.getName(), "Harry");
        found = consentService.getAccount(2);
        assertEquals(found.getName(), "Finn");
        found = consentService.getAccount(3);
        assertEquals(found.getName(), "Yeap");

    }
    @Test
    public void getAccount_withInvalidId() {
        assertEquals(consentService.getAccount(-1), null);
        assertEquals(consentService.getAccount(0), null);
        assertEquals(consentService.getAccount(1000), null);

    }

    @Test
    public void getAccountId_withValidEmail() {
        assertEquals(1, consentService.getAccountID("harry@bristol.ac.uk"));
        assertEquals(2, consentService.getAccountID("finn@bristol.ac.uk"));

    }
    @Test
    public void getAccountId_withInvalidEmail() {
        assertEquals(-1, consentService.getAccountID("leechay@bristol.ac.uk"));
        assertEquals(-1, consentService.getAccountID("tony@bristol.ac.uk"));

    }

    @Test
    public void getAccountsVisitors_noVisitors()  {
        assertEquals( new HashSet<Visitor>(), consentService.getAccountsVisitors(2));
        assertEquals( new HashSet<Visitor>(), consentService.getAccountsVisitors(3));
    }
    @Test
    public void getAccountsVisitors_withVisitors()  {
        Account a = consentService.getAccount(1);
        Iterable<Visitor> visitors = consentService.getAccountsVisitors(1);
        for (Visitor v : visitors)  {
            assertEquals("Harry Visitor", v.getName());
        }
    }


//    @Test
//    public void addNewAccount_success()  {
//        Account a = new Account("Harry", "harry@bristol.ac.uk", "password");
//        //assertTrue(consentService.addNewAccount(a));
//    }

//    @Test
//    public void updateAccountConsent_validConsentLevel()  {
//        Account found = consentService.getAccount(1);
//        assertEquals(found.getConsentLevel(), ConsentLevel.RESTRICTED);
//        assertTrue(consentService.updateAccountConsent(found.getAccountId(), ConsentLevel.NONE));
//    }


}
