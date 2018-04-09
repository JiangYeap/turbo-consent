package com.turboconsulting.Entity;

import javax.persistence.*;

@Entity
public class ConsentOption {

    @ManyToOne
    @JoinColumn(name="experimentId")
    private Experiment experiment;



    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int consentId;

    private String name;
    private String description;

    public ConsentOption() {};

    public ConsentOption(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

}
