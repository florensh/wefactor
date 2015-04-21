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
        // @formatter:off
        REGISTER("Register"), 
        LOGIN("Login"), 
        NEW_ENTRY("New entry"), 
        EDIT_ENTRY("Edit entry"), 
        REMOVE_ENTRY("Remove entry"), 
        NEW_PROPOSAL("New proposal"), 
        ACCEPT_PROPOSAL("Accept proposal"), 
        REJECT_PROPSAL("Reject proposal"), 
        JOIN_GROUP("Join group"), 
        CREATE_GROUP("Create group"), 
        LEAVE_GROUP("Leave group"), 
        RATE_ENTRY("Rate entry"), 
        REQUEST("Request");
        // @formatter:on

        private String displayName;

        EventType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return this.displayName;
        }

    }

    private Long id;

    @CreatedDate
    private Date eventOccurred = new Date();

    private String user;

    private EventType eventType;

    private String eventInformation;

    public String getEventInformation() {
        return eventInformation;
    }

    public void setEventInformation(String eventInformation) {
        this.eventInformation = eventInformation;
    }

    public JournalEntry(String name, EventType eventType) {
        this.user = name;
        this.eventType = eventType;
    }

    public JournalEntry(String name, EventType eventType, String info) {
        this.user = name;
        this.eventType = eventType;
        this.eventInformation = info;
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
