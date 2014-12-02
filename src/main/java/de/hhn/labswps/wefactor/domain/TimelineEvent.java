package de.hhn.labswps.wefactor.domain;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.hhn.labswps.wefactor.specification.WeFactorValues;

@Entity
@Table(name = "timelineEvent")
public class TimelineEvent extends BaseEntity {

    private Date eventDate;
    private UserProfile source;
    private Account target;
    private String eventType;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String type) {
        this.eventType = type;
    }

    private ObjectIdentification objectReference;

    public Date getEventDate() {
        return eventDate;
    }

    @Embedded
    public ObjectIdentification getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(ObjectIdentification objectReference) {
        this.objectReference = objectReference;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Transient
    public String getAction() {
        return WeFactorValues.EventType.valueOf(eventType).getText();
    }

    @ManyToOne
    @JoinColumn(name = "source", nullable = false)
    public UserProfile getSource() {
        return source;
    }

    public void setSource(UserProfile source) {
        this.source = source;
    }

    @ManyToOne
    @JoinColumn(name = "target", nullable = false)
    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
    }

}