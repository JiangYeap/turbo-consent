package com.turboconsulting.Entity;

import javax.persistence.*;
import java.util.GregorianCalendar;

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

    @Transient
    private ConsentOption consentOption;

    private GregorianCalendar date;

    public Visitor getVisitor() {
        return visitor;
    }

    private boolean changedConsent;

    public VisitorExperiment(){}

    public VisitorExperiment(Visitor v, Experiment e) {
        this.visitor = v;
        this.experiment = e;
        this.consentOption = v.getDefaultConsent();
        this.changedConsent = false;
        this.date = new GregorianCalendar();
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public ConsentOption getConsentOption() {
        return consentOption;
    }

    public boolean setConsentOption(ConsentOption consentLevel) {
        if(!experiment.getConsentOptions().contains(consentLevel))  return false;
        changedConsent = true;
        this.consentOption = consentLevel;
        return true;
    }

    public int getCompoundKey() {
        return compoundKey;
    }
    public void setCompoundKey(int compoundKey) {
        this.compoundKey = compoundKey;
    }

    public boolean getConsentChanged() {
        return changedConsent;
    }
    public void setChangedConsent(boolean changedConsent) {
        this.changedConsent = changedConsent;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public GregorianCalendar getDate() {
        return date;
    }
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }
}
