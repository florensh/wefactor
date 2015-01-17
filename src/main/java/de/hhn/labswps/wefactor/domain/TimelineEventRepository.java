package de.hhn.labswps.wefactor.domain;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface for operations on a repository for the type {@link TimelineEvent}.
 */
public interface TimelineEventRepository extends
        CrudRepository<TimelineEvent, Long> {

    /** The Constant DEFAULT_PAGE_SIZE. */
    static final int DEFAULT_PAGE_SIZE = 6;

    /**
     * Find by target account order by event date desc.
     *
     * @param a
     *            the a
     * @param topFive
     *            the top five
     * @return the list
     */
    List<TimelineEvent> findByTargetAccountOrderByEventDateDesc(Account a,
            Pageable topFive);

    /**
     * Find by target account or target group in order by event date desc.
     *
     * @param a
     *            the a
     * @param groups
     *            the groups
     * @param topFive
     *            the top five
     * @return the list
     */
    List<TimelineEvent> findByTargetAccountOrTargetGroupInOrderByEventDateDesc(
            Account a, Set<Group> groups, Pageable topFive);

    /**
     * Find by target account and read by user.
     *
     * @param a
     *            the a
     * @param isRead
     *            the is read
     * @return the list
     */
    @Query("select e from  TimelineEvent e where e.targetAccount = :account and e.readByUser = :isRead")
    List<TimelineEvent> findByTargetAccountAndReadByUser(
            @Param("account") Account a, @Param("isRead") boolean isRead);

    /**
     * Find by target account or target group in and read by user.
     *
     * @param a
     *            the a
     * @param groups
     *            the groups
     * @param isRead
     *            the is read
     * @return the list
     */
    @Query("select e from  TimelineEvent e where (e.targetAccount = :account or e.targetGroup in (:groups)) and e.readByUser = :isRead")
    List<TimelineEvent> findByTargetAccountOrTargetGroupInAndReadByUser(
            @Param("account") Account a, @Param("groups") Set<Group> groups,
            @Param("isRead") boolean isRead);

    /**
     * Find by target group order by event date desc.
     *
     * @param g
     *            the g
     * @param topFive
     *            the top five
     * @return the list
     */
    List<TimelineEvent> findByTargetGroupOrderByEventDateDesc(Group g,
            Pageable topFive);

    /**
     * Find by target group and read by user.
     *
     * @param g
     *            the g
     * @param isRead
     *            the is read
     * @return the list
     */
    List<TimelineEvent> findByTargetGroupAndReadByUser(Group g, boolean isRead);

}
