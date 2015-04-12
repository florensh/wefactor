package de.hhn.labswps.wefactor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;
import de.hhn.labswps.wefactor.service.JournalService;

@Service
public abstract class BaseController {

    @Autowired
    protected JournalService journalService;

    protected void writeEventToJournal(String username, EventType type) {
        this.journalService.writeEntry(username, type);

    }

}
