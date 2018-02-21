package com.turboconsulting.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Experiment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name, description;

    @OneToMany(mappedBy = "experiment" )
    private Set<VisitorExperiment> visitors;

    public Experiment(){};

    //When experiment is created.
    public Experiment(String name, String description) {
        this.name = name;
        this.description = description;
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

    public Set<VisitorExperiment> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<VisitorExperiment> visitors) {
        this.visitors = visitors;
    }

}
