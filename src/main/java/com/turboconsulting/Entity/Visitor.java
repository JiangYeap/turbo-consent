package com.turboconsulting.Entity;

import com.turboconsulting.Service.ConsentService;

import java.util.*;
import javax.persistence.*;

@Entity
public class Visitor {



    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int visitorId;

    @ManyToOne
    @JoinColumn(name="accountId", nullable = false)
    private Account account;

    private String name;
    private GregorianCalendar dob;

    private ConsentLevel defaultConsent;

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    private Set<VisitorExperiment> experiments;

    public Visitor(){}

    public Visitor(String name, GregorianCalendar dob, ConsentLevel c) {
        this.name = name;
        this.dob = dob;
        this.defaultConsent = c;
        experiments = new HashSet<>();

    }

    public void setExperiments(HashSet<VisitorExperiment> experiments) {
        this.experiments = experiments;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public ConsentLevel getDefaultConsent() {
        return defaultConsent;
    }
    public void setDefaultConsent(ConsentLevel defaultConsent) {
        this.defaultConsent = defaultConsent;
    }

    public void setDob(GregorianCalendar dob) {
        this.dob = dob;
    }
    public GregorianCalendar getDob() {
        return dob;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }
    public int getVisitorId() {
        return visitorId;
    }


    public void setExperiments(Set<VisitorExperiment> experiments) {
        this.experiments = experiments;
    }
    public Collection<VisitorExperiment> getExperiments() {
        return experiments;
    }

    public void doExperiment(VisitorExperiment e) {
        experiments.add(e);
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
