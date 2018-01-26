package com.turboconsulting.Entity;

public class ExperimentConsent {
    private ConsentLevel consentLevel;
    private Experiment experiment;

    public ExperimentConsent(ConsentLevel consentLevel, Experiment experiment) {
        this.consentLevel = consentLevel;
        this.experiment = experiment;
    }

    public ConsentLevel getConsentLevel() {
        return consentLevel;
    }

    public void setConsentLevel(ConsentLevel consentLevel) {
        this.consentLevel = consentLevel;
    }

    public Experiment getExperiment() {
        return experiment;
    }
}
