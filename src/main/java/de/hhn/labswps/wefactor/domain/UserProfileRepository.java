package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for operations on a repository for the type {@link UserProfile}.
 */
@Repository
public interface UserProfileRepository extends
        CrudRepository<UserProfile, Long> {

    /**
     * Find by username.
     *
     * @param theUserName
     *            the name of the user
     * @return the user profile
     */
    UserProfile findByUsername(String theUserName);

    /**
     * Find by email.
     *
     * @param email
     *            the email
     * @return the user profile
     */
    UserProfile findByEmail(String email);

    /**
     * Find by password.
     *
     * @param pw
     *            the password
     * @return the list of UserProfiles
     */
    List<UserProfile> findByPassword(String pw);

}
