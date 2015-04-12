package de.hhn.labswps.wefactor.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "journal")
public class JournalEntry {

    public enum EventType {
        REGISTER, LOGIN, NEW_ENTRY, EDIT_ENTRY, REMOVE_ENTRY, NEW_PROPOSAL, ACCEPT_PROPOSAL, REJECT_PROPSAL, JOIN_GROUP, CREATE_GROUP, LEAVE_GROUP, RATE_ENTRY
    }

    private Long id;

    @CreatedDate
    private Date eventOccurred = new Date();

    private String user;

    private EventType eventType;

    public JournalEntry(String name, EventType eventType) {
        this.user = name;
        this.eventType = eventType;
    }

    private JournalEntry() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getEventOccurred() {
        return eventOccurred;
    }

    public void setEventOccurred(Date eventOccurred) {
        this.eventOccurred = eventOccurred;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

}
