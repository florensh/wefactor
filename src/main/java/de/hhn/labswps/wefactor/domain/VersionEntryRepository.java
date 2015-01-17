package de.hhn.labswps.wefactor.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for operations on a repository for the type {@link VersionEntry}.
 */
public interface VersionEntryRepository extends
        CrudRepository<VersionEntry, Long> {

}
