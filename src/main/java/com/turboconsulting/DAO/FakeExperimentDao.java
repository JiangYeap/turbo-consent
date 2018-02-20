package com.turboconsulting.DAO;

import com.turboconsulting.Entity.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("experimentData")
public class FakeExperimentDao {

    @Autowired
    private static Map<Integer, Experiment> experiments;

    static {
        experiments = new HashMap<Integer, Experiment>(){
            {
                put(0, new Experiment("Chemistry", "Just your avg chemistry experiment description"));
                put(1, new Experiment("Physics", "Just your avg physics experiment description"));

            }
        };

    }

    public Collection<Experiment> getAllExperiments () {
        return this.experiments.values();
    }

}
