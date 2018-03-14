package com.turboconsulting.Entity;

import javax.persistence.*;

@Entity
public class VisitorExperiment {

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

    public VisitorExperiment(){}

    public VisitorExperiment(Visitor v, Experiment e, ConsentLevel consentLevel) {
        this.visitor = v;
        this.experiment=e;
        this.consentLevel = v.getDefaultConsent();
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

    public Experiment getExperiment() {
        return experiment;
    }

}
