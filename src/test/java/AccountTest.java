import com.turboconsulting.Entity.Account;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {

    private Account testAccount = new Account("Finn", "hw16471@bristol.ac.uk", "password");

    @Test
    public void exampleTest() {
        assertEquals("Finn", testAccount.getName());
        assertEquals("Finn", testAccount.getName());

    }

}
