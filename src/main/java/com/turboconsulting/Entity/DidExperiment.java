package com.turboconsulting.Entity;

import javax.persistence.*;

@Entity
public class DidExperiment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int compoundKey;

    @ManyToOne
    @JoinColumn(name="visitorId", nullable = false)
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name="experimentId", nullable = false)
    private Experiment experiment;

    private ConsentLevel consentLevel;

    public DidExperiment(){}

    public DidExperiment(Visitor v, Experiment e) {
        this.visitor = v;
        this.experiment=e;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public ConsentLevel getConsentLevel() {
        return consentLevel;
    }
    public void setConsentLevel(ConsentLevel consentLevel) {
        this.consentLevel = consentLevel;
    }

    public int getCompoundKey() {
        return compoundKey;
    }
    public void setCompoundKey(int compoundKey) {
        this.compoundKey = compoundKey;
    }
}
