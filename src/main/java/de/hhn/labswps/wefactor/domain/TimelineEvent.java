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

/**
 * The Class TimelineEvent.
 */
@Entity
@Table(name = "timelineEvent")
public class TimelineEvent extends BaseEntity {

    /** The event date. */
    private Date eventDate;

    /** The source. */
    private Account source;

    /** The target account. */
    private Account targetAccount;

    /** The target group. */
    private Group targetGroup;

    /** The read by user. */
    private boolean readByUser;

    /**
     * Checks if is read by user.
     *
     * @return true, if is read by user
     */
    @Column(name = "readByUser", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    public boolean isReadByUser() {
        return this.readByUser;
    }

    /**
     * Sets the read by user.
     *
     * @param isRead
     *            the new read by user
     */
    public void setReadByUser(final boolean isRead) {
        this.readByUser = isRead;
    }

    /**
     * Gets the target group.
     *
     * @return the target group
     */
    @ManyToOne
    @JoinColumn(name = "target_group", nullable = true)
    public Group getTargetGroup() {
        return this.targetGroup;
    }

    /**
     * Sets the target group.
     *
     * @param targetGroup
     *            the new target group
     */
    public void setTargetGroup(final Group targetGroup) {
        this.targetGroup = targetGroup;
    }

    /** The event type. */
    private EventType eventType;

    /**
     * Instantiates a new timeline event.
     */
    public TimelineEvent() {

    }

    /**
     * Instantiates a new timeline event.
     *
     * @param eventDate
     *            the event date
     * @param source
     *            the source
     * @param target
     *            the target
     * @param type
     *            the type
     * @param objectReference
     *            the object reference
     */
    public TimelineEvent(final Date eventDate, final Account source,
            final Account target, final EventType type,
            final ObjectIdentification objectReference) {

        this.eventDate = eventDate;
        this.source = source;
        this.targetAccount = target;
        this.eventType = type;
        this.objectReference = objectReference;

    }

    /**
     * Instantiates a new timeline event.
     *
     * @param date
     *            the date
     * @param source
     *            the source
     * @param group
     *            the group
     * @param type
     *            the type
     * @param oid
     *            the oid
     */
    public TimelineEvent(final Date date, final Account source,
            final Group group, final EventType type,
            final ObjectIdentification oid) {
        this.eventDate = date;
        this.source = source;
        this.targetGroup = group;
        this.eventType = type;
        this.objectReference = oid;
    }

    /**
     * Gets the event type.
     *
     * @return the event type
     */
    public EventType getEventType() {
        return this.eventType;
    }

    /**
     * Sets the event type.
     *
     * @param type
     *            the new event type
     */
    public void setEventType(final EventType type) {
        this.eventType = type;
    }

    /** The object reference. */
    private ObjectIdentification objectReference;

    /**
     * Gets the event date.
     *
     * @return the event date
     */
    public Date getEventDate() {
        return this.eventDate;
    }

    /**
     * Gets the object reference.
     *
     * @return the object reference
     */
    @Embedded
    public ObjectIdentification getObjectReference() {
        return this.objectReference;
    }

    /**
     * Sets the object reference.
     *
     * @param objectReference
     *            the new object reference
     */
    public void setObjectReference(final ObjectIdentification objectReference) {
        this.objectReference = objectReference;
    }

    /**
     * Sets the event date.
     *
     * @param eventDate
     *            the new event date
     */
    public void setEventDate(final Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    @Transient
    public String getAction() {
        return this.eventType.getText();
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    @ManyToOne
    @JoinColumn(name = "source", nullable = false)
    public Account getSource() {
        return this.source;
    }

    /**
     * Sets the source.
     *
     * @param source
     *            the new source
     */
    public void setSource(final Account source) {
        this.source = source;
    }

    /**
     * Gets the target account.
     *
     * @return the target account
     */
    @ManyToOne
    @JoinColumn(name = "target_account", nullable = true)
    public Account getTargetAccount() {
        return this.targetAccount;
    }

    /**
     * Sets the target account.
     *
     * @param target
     *            the new target account
     */
    public void setTargetAccount(final Account target) {
        this.targetAccount = target;
    }

}
