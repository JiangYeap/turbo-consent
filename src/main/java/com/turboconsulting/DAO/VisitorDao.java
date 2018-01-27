package com.turboconsulting.DAO;

import com.turboconsulting.Entity.LoginDetails;
import com.turboconsulting.Entity.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


@Repository
@Qualifier("visitorData")
public class VisitorDao {

    @Autowired
    private static Map<Integer, Visitor> visitors;

    static {
        visitors = new HashMap<Integer, Visitor>(){
            {
                put(0, new Visitor(0, "hw16471", "password", "Harry Waugh",
                        new GregorianCalendar(1997, 10, 10)));
                put(1, new Visitor(1, "tz12345", "pword1", "Tony",
                        new GregorianCalendar(1998, 05, 3)));
            }
        };

    }

    public Collection<Visitor> getAllVisitors () {
        return this.visitors.values();
    }

    public void updateVisitor(LoginDetails newLogin) {
        for (Visitor v : this.visitors.values())  {
            if (v.getUname().equals(newLogin.uname))  {
                v.setPassword(newLogin.pword);
            }
        }
    }
}
