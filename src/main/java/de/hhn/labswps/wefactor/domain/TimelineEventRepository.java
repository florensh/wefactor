package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TimelineEventRepository extends
        CrudRepository<TimelineEvent, Long> {

    List<TimelineEvent> findByTargetOrderByEventDateDesc(Account a,
            Pageable topFive);

}
