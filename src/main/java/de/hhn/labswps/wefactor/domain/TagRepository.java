package de.hhn.labswps.wefactor.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface for operations on a repository for the type {@link Tag}.
 */
public interface TagRepository extends CrudRepository<Tag, Long> {

    /**
     * Find by name.
     *
     * @param name
     *            the name
     * @return the tag
     */
    Tag findByName(String name);

}
