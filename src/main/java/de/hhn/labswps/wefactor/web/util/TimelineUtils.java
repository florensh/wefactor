package de.hhn.labswps.wefactor.web.util;

import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;

public class TimelineUtils {

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    public boolean setRead(TimelineEvent event) {
        event.setReadByUser(true);
        this.timelineEventRepository.save(event);
        return true;
    }

}
