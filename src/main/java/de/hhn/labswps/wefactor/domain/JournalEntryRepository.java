package de.hhn.labswps.wefactor.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;

public interface JournalEntryRepository extends
        CrudRepository<JournalEntry, Long> {

    @Query("select hour( eventOccurred ), count(*) as requests, count(distinct e.user) from JournalEntry e where  e.eventType = :type and e.eventOccurred >= :beginDate and e.eventOccurred <= :endDate group by hour( e.eventOccurred )")
    List<Object[]> get24HourStatistic(@Param("type") EventType t,
            @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

}