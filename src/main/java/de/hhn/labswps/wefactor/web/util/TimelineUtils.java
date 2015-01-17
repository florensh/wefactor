package de.hhn.labswps.wefactor.web.util;

import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;

/**
 * The Class TimelineUtils.
 */
public class TimelineUtils {

    /** The timeline event repository. */
    @Autowired
    private TimelineEventRepository timelineEventRepository;

    /**
     * Sets the read.
     *
     * @param event
     *            the event
     * @return true, if successful
     */
    public boolean setRead(final TimelineEvent event) {
        event.setReadByUser(true);
        this.timelineEventRepository.save(event);
        return true;
    }

}
