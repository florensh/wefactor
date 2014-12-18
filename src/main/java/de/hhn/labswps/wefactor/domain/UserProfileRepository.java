package de.hhn.labswps.wefactor.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends
        CrudRepository<UserProfile, Long> {

    UserProfile findByUsername(String u);

    UserProfile findByName(String u);

    UserProfile findByEmail(String email);

}
