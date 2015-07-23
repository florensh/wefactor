package de.hhn.labswps.wefactor.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;
import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;

@Service
public class NotificationService {

    /** The timeline event repository. */
    @Autowired
    private TimelineEventRepository timelineEventRepository;

    public void sendToTimeline(Account source, Account target,
            EventType eventType, ObjectIdentification oid) {
        final TimelineEvent event = new TimelineEvent(new Date(), source,
                target, EventType.PROPOSAL_ACCEPTED, oid);

        this.timelineEventRepository.save(event);

    }

    public void sendToTimeline(Account account, Group group,
            EventType madeEntry, ObjectIdentification oid) {
        final TimelineEvent event = new TimelineEvent(new Date(), account,
                group, EventType.MADE_ENTRY, oid);

        this.timelineEventRepository.save(event);

    }

    public void sendMailNotificationsForEvent(Account source, Account target,
            EventType eventType, ObjectIdentification oid) {
        // TODO Auto-generated method stub

    }

    public void sendMailNotificationsForEvent(Account account, Group group,
            EventType madeEntry, ObjectIdentification oid) {
        // TODO Auto-generated method stub

    }

}
