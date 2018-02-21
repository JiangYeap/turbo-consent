package com.turboconsulting.Entity;

import java.util.*;
import javax.persistence.*;

@Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int visitorId;

    private String uname, name, password;
    private GregorianCalendar dob;

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    private Set<VisitorExperiment> experiments;

    public Visitor(){}

    public Visitor(String uname, String password, String name, GregorianCalendar dob) {
        this.uname = uname;
        this.password = password;
        this.name = name;
        this.dob = dob;
        experiments = new HashSet<VisitorExperiment>();

    }


    public Collection<VisitorExperiment> getExperiments() {
        return experiments;
    }

    public void setExperiments(HashSet<VisitorExperiment> experiments) {
        this.experiments = experiments;
    }


    public String getUname() {
        return uname;
    }

    public String getName() {
        return name;
    }

    public GregorianCalendar getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(GregorianCalendar dob) {
        this.dob = dob;
    }

    public void doExperiment(VisitorExperiment e) {
        experiments.add(e);
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public void setExperiments(Set<VisitorExperiment> experiments) {
        this.experiments = experiments;
    }
}
