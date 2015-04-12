package de.hhn.labswps.wefactor.domain;

import org.springframework.data.repository.CrudRepository;

public interface JournalEntryRepository extends
        CrudRepository<JournalEntry, Long> {

}