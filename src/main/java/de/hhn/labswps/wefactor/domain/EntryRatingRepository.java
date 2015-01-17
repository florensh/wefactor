package de.hhn.labswps.wefactor.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for operations on a repository for the type {@link EntryRating}.
 */
public interface EntryRatingRepository extends
        CrudRepository<EntryRating, Long> {

}
