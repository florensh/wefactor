package de.hhn.labswps.wefactor.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserConnectionRepository extends
        CrudRepository<UserConnection, Long> {

    // Set<String> findUserIdsByProviderIdAndProviderUserIds(String providerId,
    // Set<String> providerUserIds);

    // List<String> findUserIdsByProviderIdAndProviderUserId(String providerId,
    // String providerUserId);

    List<UserConnection> findByProviderId(String providerId);

    @Modifying
    @Transactional
    @Query("delete from UserConnection u where u.userId = ?1 and u.providerId = ?2 and u.providerUserId = ?3")
    Long deleteByUserIdAndProviderIdAndProviderUserId(String userId,
            String providerId, String providerUserId);

    @Modifying
    @Transactional
    @Query("delete from UserConnection u where u.providerId = ?1")
    Long deleteByProviderId(String providerId);

    List<UserConnection> findByProviderIdAndProviderUserIdIn(String providerId,
            List<String> providerUserIds);

    List<UserConnection> findByProviderIdAndProviderUserId(String providerId,
            String providerUserId);

    List<UserConnection> findByUserIdAndProviderIdOrderByRankDesc(
            String userId, String providerId);

    UserConnection findByUserIdAndProviderIdAndProviderUserId(String userId,
            String providerId, String providerUserId);

    UserConnection findByUserId(String userId);

    UserConnection findByProviderUserId(String userId);

}
