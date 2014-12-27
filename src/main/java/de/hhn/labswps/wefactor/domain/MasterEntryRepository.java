package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The Interface EntryRepository.
 */
public interface MasterEntryRepository extends
        CrudRepository<MasterEntry, Long> {

    List<Entry> findByAccountId(Long accountId);

    List<Entry> findDistinctByGroupIsNullOrGroupMembers(Account a);

    List<Entry> findDistinctByEntryDescriptionContainingOrNameContainingOrTeaserContainingOrAccountProfilesNameContainingAndGroupIsNullOrGroupMembers(
            String d_searchtext, String n_searchtext, String t_searchtext,
            String pn_searchtext, Account a);

    // @formatter:off
    @Query("select e "
            + "from MasterEntry e "
            + "left join e.group.members account "
            + "where ("
            + "e.entryDescription like %:searchText% "
            + "or e.name like %:searchText% "
            + "or e.teaser like %:searchText% "
            + "or exists(from UserProfile u where u.name like %:searchText% and u.account = e.account )"
            + ") "
            + "and ("
            + "e.group is null "
            + "or "
            + "account = :account)")
    // @formatter:on
    List<Entry> search(@Param("searchText") String searchText,
            @Param("account") Account account);

}
