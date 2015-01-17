package de.hhn.labswps.wefactor.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for operations on a repository for the type {@link ProposalEntry}.
 */
public interface ProposalEntryRepository extends
        CrudRepository<ProposalEntry, Long> {

}
