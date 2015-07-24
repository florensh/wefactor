package de.hhn.labswps.wefactor.service;

import java.security.Principal;
import java.util.Date;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;
import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.service.util.MailManager;
import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;

@Service
public class NotificationService {

    /** The timeline event repository. */
    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @Autowired
    private MailManager mailManager;

    @Value("${doSending}")
    private Boolean doSending;

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

    public void sendMailNotificationsForEvent(Account source,
            Set<Account> target, EventType eventType, ObjectIdentification oid,
            String url, boolean sendToCurrentUser, Principal currentUser) {

        if (Boolean.FALSE.equals(this.doSending)) {
            return;
        }

        String body = source.getPrimaryProfile().getName() + " "
                + eventType.getText() + " > " + oid.getOidName() + "\n\r" + url
                + eventType.getLink() + "?id=" + oid.getOidIdentification();
        String subject = eventType.getMailSubject();

        for (Account a : target) {
            if (a.getPrimaryProfile().getUsername()
                    .equals(currentUser.getName())) {
                continue;
            }
            String mailAdress = a.getPrimaryProfile().getEmail();

            try {
                this.mailManager.sendMessage(mailAdress, subject, body);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }

    }
}
