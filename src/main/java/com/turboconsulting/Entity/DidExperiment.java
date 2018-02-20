package com.turboconsulting.Entity;

import javax.persistence.Entity;


public class DidExperiment {
    private int userID;
    private int experimentID;

    public DidExperiment(){}

    public DidExperiment(int userID, int experimentID) {
        this.userID = userID;
        this.experimentID = experimentID;
    }
}
