package de.hhn.labswps.wefactor.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface UserProfileRepository.
 */
@Repository
public interface UserProfileRepository extends
        CrudRepository<UserProfile, Long> {

    /**
     * Find by username.
     *
     * @param u
     *            the u
     * @return the user profile
     */
    UserProfile findByUsername(String u);

    /**
     * Find by email.
     *
     * @param email
     *            the email
     * @return the user profile
     */
    UserProfile findByEmail(String email);

}
