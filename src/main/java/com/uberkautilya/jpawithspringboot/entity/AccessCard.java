package com.uberkautilya.jpawithspringboot.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class AccessCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date issueDate;
    private boolean isActive;
    private String firmwareVersion;
    /**
     * A mappedBy property here tells that the owner is Employee - primary relationship, while AccessCard's backlink is the secondary one
     * In short it tells this object is the accessCard referred in the Employee - directionality
     * Thus when accessing an accessCard, JPA would not fetch from DB the accessCard of the related employee - avoid circularity
     */
    @OneToOne(mappedBy = "accessCard")
    private Employee owner;

    public AccessCard() {
    }

    public AccessCard(Date issueDate, boolean isActive, String firmwareVersion) {
        this.issueDate = issueDate;
        this.isActive = isActive;
        this.firmwareVersion = firmwareVersion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "AccessCard{" +
                "id=" + id +
                ", issueDate=" + issueDate +
                ", isActive=" + isActive +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                '}';
    }
}
