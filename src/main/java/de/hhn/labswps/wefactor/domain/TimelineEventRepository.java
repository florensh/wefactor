package de.hhn.labswps.wefactor.domain;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TimelineEventRepository extends
        CrudRepository<TimelineEvent, Long> {

    static final int DEFAULT_PAGE_SIZE = 6;

    List<TimelineEvent> findByTargetAccountOrderByEventDateDesc(Account a,
            Pageable topFive);

    List<TimelineEvent> findByTargetAccountOrTargetGroupInOrderByEventDateDesc(
            Account a, Set<Group> groups, Pageable topFive);

    @Query("select e from  TimelineEvent e where e.targetAccount = :account and e.readByUser = :isRead")
    List<TimelineEvent> findByTargetAccountAndReadByUser(
            @Param("account") Account a, @Param("isRead") boolean isRead);

    @Query("select e from  TimelineEvent e where (e.targetAccount = :account or e.targetGroup in (:groups)) and e.readByUser = :isRead")
    List<TimelineEvent> findByTargetAccountOrTargetGroupInAndReadByUser(
            @Param("account") Account a, @Param("groups") Set<Group> groups,
            @Param("isRead") boolean isRead);

    List<TimelineEvent> findByTargetGroupOrderByEventDateDesc(Group g,
            Pageable topFive);

    List<TimelineEvent> findByTargetGroupAndReadByUser(Group g, boolean isRead);

}
