package de.hhn.labswps.wefactor.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;

@Entity
@Table(name = "timelineEvent")
public class TimelineEvent extends BaseEntity {

    private Date eventDate;
    private Account source;
    private Account targetAccount;
    private Group targetGroup;

    private boolean readByUser;

    @Column(name = "readByUser", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    public boolean isReadByUser() {
        return readByUser;
    }

    public void setReadByUser(boolean isRead) {
        this.readByUser = isRead;
    }

    @ManyToOne
    @JoinColumn(name = "target_group", nullable = true)
    public Group getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(Group targetGroup) {
        this.targetGroup = targetGroup;
    }

    private EventType eventType;

    public TimelineEvent() {

    }

    public TimelineEvent(Date eventDate, Account source, Account target,
            EventType type, ObjectIdentification objectReference) {

        this.eventDate = eventDate;
        this.source = source;
        this.targetAccount = target;
        this.eventType = type;
        this.objectReference = objectReference;

    }

    public TimelineEvent(Date date, Account source, Group group,
            EventType type, ObjectIdentification oid) {
        this.eventDate = date;
        this.source = source;
        this.targetGroup = group;
        this.eventType = type;
        this.objectReference = oid;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType type) {
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
        return eventType.getText();
    }

    @ManyToOne
    @JoinColumn(name = "source", nullable = false)
    public Account getSource() {
        return source;
    }

    public void setSource(Account source) {
        this.source = source;
    }

    @ManyToOne
    @JoinColumn(name = "target_account", nullable = true)
    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account target) {
        this.targetAccount = target;
    }

}
