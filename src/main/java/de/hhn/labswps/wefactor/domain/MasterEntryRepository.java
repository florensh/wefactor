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

    /**
     * Find by account id.
     *
     * @param accountId
     *            the account id
     * @return the list
     */
    List<Entry> findByAccountId(Long accountId);

    /**
     * Find distinct by group is null or group members.
     *
     * @param a
     *            the a
     * @return the list
     */
    List<Entry> findDistinctByGroupIsNullOrGroupMembersOrderByEntryDateDesc(Account a);

    /**
     * Find distinct by entry description containing or name containing or
     * teaser containing or account profiles name containing and group is null
     * or group members.
     *
     * @param d_searchtext
     *            the d_searchtext
     * @param n_searchtext
     *            the n_searchtext
     * @param t_searchtext
     *            the t_searchtext
     * @param pn_searchtext
     *            the pn_searchtext
     * @param a
     *            the a
     * @return the list
     */
    List<Entry> findDistinctByEntryDescriptionContainingOrNameContainingOrTeaserContainingOrAccountProfilesNameContainingAndGroupIsNullOrGroupMembers(
            String d_searchtext, String n_searchtext, String t_searchtext,
            String pn_searchtext, Account a);

    // @formatter:off
    /**
     * Search.
     *
     * @param searchText the search text
     * @param account the account
     * @return the list
     */
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

    /**
     * Find distinct by tags name or versions tags name.
     *
     * @param mTag
     *            the m tag
     * @param vTag
     *            the v tag
     * @return the list
     */
    List<Entry> findDistinctByTagsNameOrVersionsTagsName(String mTag,
            String vTag);

}
