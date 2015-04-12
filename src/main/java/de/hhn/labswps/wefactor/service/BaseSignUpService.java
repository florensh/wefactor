package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;

@Service
public abstract class BaseSignUpService {

    @Autowired
    protected JournalService journalService;

    protected void writeEventToJournal(String username, EventType type) {
        this.journalService.writeEntry(username, type);

    }

}
