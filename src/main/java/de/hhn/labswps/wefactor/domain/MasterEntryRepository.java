package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface EntryRepository.
 */
public interface MasterEntryRepository extends
        CrudRepository<MasterEntry, Long> {

    List<Entry> findByAccountId(Long accountId);

    List<Entry> findByEntryDescriptionContainingOrNameContainingOrTeaserContainingOrAccountProfilesNameContaining(
            String d_searchtext, String n_searchtext, String t_searchtext,
            String pn_searchtext);

    List<Entry> findDistinctByTagsNameOrVersionsTagsName(String mTag, String vTag);

}
