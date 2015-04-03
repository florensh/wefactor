package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for operations on a repository for the type {@link ProposalEntry}.
 */
public interface ProposalEntryRepository extends
        CrudRepository<ProposalEntry, Long> {

    List<ProposalEntry> findByAccountAndNameAndEntryDescriptionAndLanguageAndChangesAndTeaserAndEntryCodeTextAndMasterOfProposalAndStatus(
            Account account, String title, String description, String language,
            String changes, String teaser, String code, MasterEntry toSave,
            String name);

}
