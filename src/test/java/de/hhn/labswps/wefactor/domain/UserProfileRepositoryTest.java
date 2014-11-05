package de.hhn.labswps.wefactor.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseTest;

public class UserProfileRepositoryTest extends BaseTest {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    public void basicTest() {

        IUserProfile userProfile = new UserProfile("1", "name", "firstName",
                "secondName", "mail@mail.de", "username");
        userProfileRepository.save((UserProfile) userProfile);

    }

}
