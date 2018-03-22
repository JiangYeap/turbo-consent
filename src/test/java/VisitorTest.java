import com.turboconsulting.Entity.ConsentLevel;
import com.turboconsulting.Entity.Visitor;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.GregorianCalendar;

public class VisitorTest {

    private GregorianCalendar dob = new GregorianCalendar(1998, 7, 14);
    private Visitor testVistor = new Visitor("Finn", dob, ConsentLevel.RESTRICTED);

    @Test
    public void exampleTest() {
        assertEquals("Finn", testVistor.getName());
    }
}
