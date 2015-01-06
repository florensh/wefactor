package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface GroupRepository.
 */
public interface GroupRepository extends CrudRepository<Group, Long> {

    /**
     * Find by name containing.
     *
     * @param name
     *            the name
     * @return the list
     */
    List<Group> findByNameContaining(String name);

    /**
     * Find by members.
     *
     * @param a
     *            the a
     * @return the list
     */
    List<Group> findByMembers(Account a);
}
