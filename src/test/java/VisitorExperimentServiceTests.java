import com.turboconsulting.DAO.AccountDao;
import com.turboconsulting.DAO.ExperimentDao;
import com.turboconsulting.DAO.VisitorDao;
import com.turboconsulting.Service.ConsentService;
import com.turboconsulting.Service.ConsentServiceInterface;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class VisitorExperimentTests {

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


}
