import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.Experiment;
import com.turboconsulting.Entity.Visitor;
import com.turboconsulting.Entity.VisitorExperiment;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
public class VisitorExperimentServiceTests {

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
        ArrayList<Experiment> experiments = new ArrayList<>();
        ArrayList<VisitorExperiment> visitorExperiments = new ArrayList<>();


        accounts.add(mockEntityFactory.mockAccount(accountDao, "Harry", "harry@bristol.ac.uk", "password",1));
        accounts.add(mockEntityFactory.mockAccount(accountDao, "Finn", "finn@bristol.ac.uk", "password", 2));
        Mockito.when(accountDao.save(any(Account.class))).thenAnswer(AdditionalAnswers.<Account>returnsFirstArg());
        Mockito.when(accountDao.findAll()).thenReturn(accounts);

        visitors.add(mockEntityFactory.mockVisitor(visitorDao, "Harry Visitor 1", 1, accounts.get(0)));
        visitors.add(mockEntityFactory.mockVisitor(visitorDao, "Harry Visitor 2", 2, accounts.get(0)));
        Mockito.when(visitorDao.save(any(Visitor.class))).thenAnswer(AdditionalAnswers.<Visitor>returnsFirstArg());
        Mockito.when(visitorDao.findAll()).thenReturn(visitors);


        experiments.add(mockEntityFactory.mockExperiment(experimentDao, "Physics Experiment", "Description 1", 1));
        experiments.add(mockEntityFactory.mockExperiment(experimentDao, "Chemistry Experiment", "Description 2", 2));
        Mockito.when(experimentDao.save(any(Experiment.class))).thenAnswer(AdditionalAnswers.<Account>returnsFirstArg());
        Mockito.when(experimentDao.findAll()).thenReturn(experiments);

        visitorExperiments.add(mockEntityFactory.mockVisitorExperiment(visitorDao, experimentDao, 1, 1));
        visitorExperiments.add(mockEntityFactory.mockVisitorExperiment(visitorDao, experimentDao, 1, 1));

    }

    @Test
    public void getVisitorExperiments_success()  {
        Iterable<Experiment> experiments = consentService.getVisitorExperiments(1);
        for ( Experiment experiment : experiments)  {
            assertEquals(experiment.getName(), "Physics Experiment");
        }
    }

    @Test
    public void addVisitorExperiment_success()  {
        assertTrue(consentService.doExperiment(1, 2));
        Iterable<Experiment> experiments = consentService.getVisitorExperiments(1);
        ArrayList<String> experimentNames = new ArrayList<>();
        experimentNames.add("Physics Experiment");
        experimentNames.add("Physics Experiment");
        experimentNames.add("Chemistry Experiment");
        for ( Experiment experiment : experiments)  {
            experimentNames.remove(experiment.getName());
        }
        assertEquals(experimentNames.size(), 0);
    }

}