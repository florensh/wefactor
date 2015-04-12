package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.JournalEntry;
import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;
import de.hhn.labswps.wefactor.domain.JournalEntryRepository;

@Service
public class JournalService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void writeEntry(String currentUser, EventType eventType) {

        if (currentUser == null || currentUser.isEmpty()) {
            return;
        }

        this.journalEntryRepository.save(new JournalEntry(currentUser,
                eventType));
    }

}
