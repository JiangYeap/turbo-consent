import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.DAO.VisitorExperimentDao;
import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.LoginDetails;
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


import java.util.ArrayList;

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

        accounts.add(mockEntityFactory.mockAccount(accountDao, "Harry", "harry@bristol.ac.uk", "password", 1));
        accounts.add(mockEntityFactory.mockAccount(accountDao, "Finn", "finn@bristol.ac.uk", "password", 2));
        accounts.add(mockEntityFactory.mockAccount(accountDao, "Yeap", "yeap@bristol.ac.uk", "password", 3));
        Mockito.when(accountDao.save(any(Account.class))).thenAnswer(AdditionalAnswers.<Account>returnsFirstArg());
        Mockito.when(accountDao.findAll()).thenReturn(accounts);
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
    public void checkAccountLogin_withValidLogin()  {
        LoginDetails loginDetails = new LoginDetails("harry@bristol.ac.uk", "password");
        assertTrue(consentService.checkAccountLogin(loginDetails));
        loginDetails = new LoginDetails("yeap@bristol.ac.uk", "password");
        assertTrue(consentService.checkAccountLogin(loginDetails));
    }
    @Test
    public void checkAccountLogin_withInvalidLogin()  {
        LoginDetails loginDetails = new LoginDetails("leechay@bristol.ac.uk", "password");
        assertFalse(consentService.checkAccountLogin(loginDetails));
        loginDetails = new LoginDetails("yeap@bristol.ac.uk", "password2");
        assertFalse(consentService.checkAccountLogin(loginDetails));
    }

    @Test
    public void addNewAccount_success()  {
        Account a = new Account("Harry", "harry@bristol.ac.uk", "password");
        assertTrue(consentService.addNewAccount(a));
    }

//    @Test
//    public void updateAccountConsent_validConsentLevel()  {
//        Account found = consentService.getAccount(1);
//        assertEquals(found.getConsentLevel(), ConsentLevel.RESTRICTED);
//        assertTrue(consentService.updateAccountConsent(found.getAccountId(), ConsentLevel.NONE));
//    }


}
