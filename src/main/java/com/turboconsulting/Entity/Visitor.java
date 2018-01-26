package com.turboconsulting.Entity;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Visitor {

    private int id;
    private String uname, name, password;
    private GregorianCalendar dob;
    private ArrayList<Integer> experimentIDs;
    private ArrayList<Visitor> dependents;

    public Visitor(int id, String uname, String password, String name, GregorianCalendar dob) {
        this.id = id;
        this.uname = uname;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.experimentIDs = new ArrayList<Integer>();
        this.dependents = new ArrayList<Visitor>();
    }



    public int getId() {
        return id;
    }

    public String getUname() {
        return uname;
    }

    public String getName() {
        return name;
    }

    public GregorianCalendar getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Integer> getExperimentIDs() {
        return experimentIDs;
    }

    public void setExperimentIDs(ArrayList<Integer> experimentIDs) {
        this.experimentIDs = experimentIDs;
    }

    public ArrayList<Visitor> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<Visitor> dependents) {
        this.dependents = dependents;
    }
}
