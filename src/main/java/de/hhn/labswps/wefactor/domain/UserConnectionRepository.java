package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Interface UserConnectionRepository.
 */
public interface UserConnectionRepository extends
        CrudRepository<UserConnection, Long> {

    /**
     * Find by provider id.
     *
     * @param providerId
     *            the provider id
     * @return the list
     */
    List<UserConnection> findByProviderId(String providerId);

    /**
     * Delete by user id and provider id and provider user id.
     *
     * @param userId
     *            the user id
     * @param providerId
     *            the provider id
     * @param providerUserId
     *            the provider user id
     * @return the long
     */
    @Modifying
    @Transactional
    @Query("delete from UserConnection u where u.userId = ?1 and u.providerId = ?2 and u.providerUserId = ?3")
    Long deleteByUserIdAndProviderIdAndProviderUserId(String userId,
            String providerId, String providerUserId);

    /**
     * Delete by provider id.
     *
     * @param providerId
     *            the provider id
     * @return the long
     */
    @Modifying
    @Transactional
    @Query("delete from UserConnection u where u.providerId = ?1")
    Long deleteByProviderId(String providerId);

    /**
     * Find by provider id and provider user id in.
     *
     * @param providerId
     *            the provider id
     * @param providerUserIds
     *            the provider user ids
     * @return the list
     */
    List<UserConnection> findByProviderIdAndProviderUserIdIn(String providerId,
            List<String> providerUserIds);

    /**
     * Find by provider id and provider user id.
     *
     * @param providerId
     *            the provider id
     * @param providerUserId
     *            the provider user id
     * @return the list
     */
    List<UserConnection> findByProviderIdAndProviderUserId(String providerId,
            String providerUserId);

    /**
     * Find by user id and provider id order by rank desc.
     *
     * @param userId
     *            the user id
     * @param providerId
     *            the provider id
     * @return the list
     */
    List<UserConnection> findByUserIdAndProviderIdOrderByRankDesc(
            String userId, String providerId);

    /**
     * Find by user id and provider id and provider user id.
     *
     * @param userId
     *            the user id
     * @param providerId
     *            the provider id
     * @param providerUserId
     *            the provider user id
     * @return the user connection
     */
    UserConnection findByUserIdAndProviderIdAndProviderUserId(String userId,
            String providerId, String providerUserId);

    /**
     * Find by user id.
     *
     * @param userId
     *            the user id
     * @return the user connection
     */
    UserConnection findByUserId(String userId);

    /**
     * Find by provider user id.
     *
     * @param userId
     *            the user id
     * @return the user connection
     */
    UserConnection findByProviderUserId(String userId);

}
