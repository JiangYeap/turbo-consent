package DAO;

import Entity.Experiment;
import Entity.Visitor;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

public class ExperimentDao {

    private static Map<Integer, Visitor> experiments;

    static {
        experiments = new HashMap<Integer, Visitor>();
        {
            put(0, new Experiment(0, "Chemistry", "Just your avg chemistry experiment description"));
            put(1, new Experiment(1, "Physics", "Just your avg physics experiment description"));

        }
    }

    public Collection<Visitor> getAllExperiments () {
        return this.experiments.values();
    }

}
