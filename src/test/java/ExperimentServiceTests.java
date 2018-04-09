import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.DAO.VisitorExperimentDao;
import com.turboconsulting.Entity.Account;
import com.turboconsulting.Entity.Experiment;
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
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
public class ExperimentServiceTests {
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

        MockEntityFactory mockAccountFactory = new MockEntityFactory();
        ArrayList<Experiment> experiments = new ArrayList<>();

        experiments.add(mockAccountFactory.mockExperiment(experimentDao, "Physics Experiment", "Description 1", 1));
        experiments.add(mockAccountFactory.mockExperiment(experimentDao, "Chemistry Experiment", "Description 2", 2));

        Mockito.when(experimentDao.save(any(Experiment.class))).thenAnswer(AdditionalAnswers.<Account>returnsFirstArg());
        Mockito.when(experimentDao.findAll()).thenReturn(experiments);
    }

    @Test
    public void getExperiment_withValidId() {
        Experiment found = consentService.getExperiment(1);
        assertEquals(found.getName(), "Physics Experiment");
        found = consentService.getExperiment(2);
        assertEquals(found.getName(), "Chemistry Experiment");

    }
    @Test
    public void getExperiment_withInvalidId() {
        assertEquals(consentService.getExperiment(-1), null);
        assertEquals(consentService.getExperiment(0), null);
        assertEquals(consentService.getExperiment(1000), null);

    }

    @Test
    public void addNewExperiment_sucess() {
        Experiment e = new Experiment("Harry", "harry@bristol.ac.uk", new HashSet<>());
        assertTrue(consentService.addNewExperiment(e));
    }

}
