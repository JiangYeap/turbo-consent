package com.turboconsulting.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DidExperiment {
    @Id
    private int userID;
    private int experimentID;
    private ConsentLevel consentLevel;

    public DidExperiment(){}

    public DidExperiment(int userID, int experimentID, ConsentLevel consentLevel) {
        this.userID = userID;
        this.experimentID = experimentID;
        this.consentLevel = consentLevel;
    }
}
