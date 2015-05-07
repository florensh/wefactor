package de.hhn.labswps.wefactor.domain;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class EntrySpecs {

    public static Specification<Entry> isLongTermCustomer(Account createdBy) {
        return new Specification<Entry>() {
            public Predicate toPredicate(Root<Entry> root,
                    CriteriaQuery<?> query, CriteriaBuilder builder) {

                // LocalDate date = new LocalDate().minusYears(2);
                // return builder.lessThan(root.get(Entry.), date);
                return null;
            }
        };
    }

}
