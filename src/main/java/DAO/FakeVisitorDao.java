package DAO;

import Entity.Visitor;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

public class FakeVisitorDao {
    private static Map<Integer, Visitor> visitors;

    static {
        visitors = new HashMap<Integer, Visitor>();
        {
            put(0, new Visitor(0, "hw16471", "password", "Harry Waugh",
                    new GregorianCalendar(1997, 10, 10)));
            put(1, new Visitor(1, "tz12345", "pword1", "Tony",
                    new GregorianCalendar(1998, 05, 3)));
        }
    }

    public Collection<Visitor> getAllVisitors () {
        return this.visitors.values();
    }
}
