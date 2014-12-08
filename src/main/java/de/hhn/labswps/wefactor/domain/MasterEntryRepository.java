package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface EntryRepository.
 */
public interface MasterEntryRepository extends
        CrudRepository<MasterEntry, Long> {

    List<Entry> findByAccountId(Long accountId);

}
