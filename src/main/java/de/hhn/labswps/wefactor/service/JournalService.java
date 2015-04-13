package de.hhn.labswps.wefactor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.JournalEntry;
import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;
import de.hhn.labswps.wefactor.domain.JournalEntryRepository;

@Service
public class JournalService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void writeEntry(String currentUser, EventType eventType) {

        if (currentUser == null || currentUser.isEmpty()) {
            return;
        }

        this.journalEntryRepository.save(new JournalEntry(currentUser,
                eventType));
    }

    public void writeEntry(String currentUser, EventType eventType, String info) {
        if (currentUser == null || currentUser.isEmpty()) {
            return;
        }

        this.journalEntryRepository.save(new JournalEntry(currentUser,
                eventType, info));

    }

    public List<StatisticValue> getStatistic() {

        List<StatisticValue> list = new ArrayList<StatisticValue>();

        for (int i = 0; i < 24; i++) {
            list.add(new StatisticValue(transformToHour(i)));
        }

        List<Object[]> items = this.journalEntryRepository
                .get24HourStatistic(EventType.REQUEST);

        if (items != null && !items.isEmpty()) {

            for (Object item : items) {
                Object[] tuple = (Object[]) item;
                Integer hour = (Integer) tuple[0];
                Long count = (Long) tuple[1];
                Long users = (Long) tuple[2];
                String key = transformToHour(hour);
                StatisticValue sv = resolveValue(list, key);
                sv.value = count;
                sv.users = users;

            }
        }

        return list;
    }

    private StatisticValue resolveValue(List<StatisticValue> list, String key) {
        for (StatisticValue sv : list) {
            if (sv.key.equals(key)) {
                return sv;
            }
        }
        return null;

    }

    private String transformToHour(Integer hour) {
        return String.valueOf(hour).concat(":00");
    }

    public class StatisticValue {
        public StatisticValue(String k) {
            this.key = k;
        }

        public String key;
        public Long users = 0L;
        public Long value = 0L;
    }
}
