package de.hhn.labswps.wefactor.web.DataObjects;

import java.util.Date;

import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;

public class AdminStatisticsDataObject {

    private Date day;
    private EventType eventType;

    public Date getDay() {
        // default is today
        if (day == null) {
            day = new Date();
        }
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public EventType getEventType() {
        if (eventType == null) {
            eventType = EventType.REQUEST;
        }
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

}
