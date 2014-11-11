package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface EntryRepository.
 */
public interface EntryRepository extends CrudRepository<Entry, Long> {

    // @Modifying
    // @Transactional
    // @Query("select e from Entry e where e.account.id = ?1")
    List<Entry> findByAccountId(Long accountId);

}
