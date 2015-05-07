package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface for operations on a repository for the type {@link MasterEntry}.
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
    Page<Entry> findByAccountId(Long accountId, Pageable pageable);

    /**
     * Find distinct by group is null or group members.
     *
     * @param a
     *            the a
     * @return the list
     */
    Page<Entry> findDistinctByGroupIsNullOrGroupMembersOrderByEntryDateDesc(
            Account a, Pageable pageable);

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


    @Query("select distinct e "
            + "from MasterEntry e "
            + "left join e.group.members groupMembers "
            + "where ("
            + "e.entryDescription like %:searchText% "
            + "or e.name like %:searchText% "
            + "or e.teaser like %:searchText% "
            + "or exists(from UserProfile u where u.name like %:searchText% and u.account = e.account )"
            + ") "
            + "and ("
            + "e.privateEntry = false "
            + "or e.account = :account "
            + "or groupMembers = :account) "
            + "order by e.createdDate desc"
            )
    // @formatter:on
    Page<Entry> search(@Param("searchText") String searchText,
            @Param("account") Account account, Pageable pageable);

    /**
     * Find distinct by tags name or versions tags name.
     *
     * @param mTag
     *            the m tag
     * @param vTag
     *            the v tag
     * @return the list
     */
    Page<Entry> findDistinctByTagsNameOrVersionsTagsName(String mTag,
            String vTag, Pageable pageable);

    List<Entry> findByAccountAndNameAndEntryDescriptionAndLanguageAndChangesAndTeaserAndEntryCodeText(
            Account account, String title, String description, String language,
            String changes, String teaser, String code);

    Page<Entry> findByGroup(Group group, Pageable pageable);

    Page<Entry> findByProposalsAccountIdOrderByProposalsCreatedDateDesc(
            Long id, Pageable pageable);

    Page<Entry> findByVersionsAccountIdOrderByVersionsCreatedDateDesc(Long id,
            Pageable pageable);

}
