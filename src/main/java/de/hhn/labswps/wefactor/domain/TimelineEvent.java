package de.hhn.labswps.wefactor.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "timelineEvent")
public class TimelineEvent {

    /** The id. */
    private Long id;

    private Date eventDate;
    private String action;
    private UserProfile source;
    private Account target;

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param idParam
     *            the new id
     */
    public void setId(final Long idParam) {
        this.id = idParam;
    }

}
