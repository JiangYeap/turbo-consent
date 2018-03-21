package com.turboconsulting.Entity;

import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int accountId;

    private String name, email, password;
    private ConsentLevel consentLevel;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Visitor> visitors;

    public Account(){}

    public Account(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.consentLevel = ConsentLevel.RESTRICTED;
        visitors = new HashSet<>();
    }

    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public ConsentLevel getConsentLevel() {
        return consentLevel;
    }
    public void setConsentLevel(ConsentLevel consentLevel) {
        this.consentLevel = consentLevel;
    }

    public Set<Visitor> getVisitors() {
        return visitors;
    }
    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }
}
