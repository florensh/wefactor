package de.hhn.labswps.wefactor.domain;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TimelineEventRepository extends
        CrudRepository<TimelineEvent, Long> {

    static final int DEFAULT_PAGE_SIZE = 6;

    List<TimelineEvent> findByTargetAccountOrderByEventDateDesc(Account a,
            Pageable topFive);

    List<TimelineEvent> findByTargetAccountOrTargetGroupInOrderByEventDateDesc(
            Account a, Set<Group> groups, Pageable topFive);

    List<TimelineEvent> findByTargetGroupOrderByEventDateDesc(Group g,
            Pageable topFive);

}
