package com.turboconsulting.Entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Experiment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(unique=true)
    private String name;


    private String description;

    @OneToMany(mappedBy = "experiment", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    private Set<ConsentOption> consentOptions;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "experiment" )
    private Set<VisitorExperiment> visitors;

    public Experiment(){};

    //When experiment is created.
    public Experiment(String name, String description, Set<ConsentOption> customConsentOptions) {
        this.name = name;
        this.description = description;
        visitors = new HashSet<>();
        consentOptions = new HashSet<>();
        this.consentOptions.add(new ConsentOption("NO CONSENT", "This option means you do not give consent for We the Curious to use any of your data"));
        this.consentOptions.add(new ConsentOption("FULL CONSENT", "This option means you do give consent for We the Curious to use all of your data"));
        for(ConsentOption c : customConsentOptions)  c.setName(c.getName().toUpperCase());
        consentOptions.addAll(customConsentOptions);

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void doExperiment(VisitorExperiment e) {
        visitors.add(e);
    }

    public Set<ConsentOption> getConsentOptions() {
        return consentOptions;
    }

    public void setConsentOptions(Set<ConsentOption> consentOptions) {
        this.consentOptions = consentOptions;
    }


    public void setVisitors(Set<VisitorExperiment> visitors) {
        this.visitors = visitors;
    }

}
