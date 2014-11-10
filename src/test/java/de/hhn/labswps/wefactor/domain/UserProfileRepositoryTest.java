package de.hhn.labswps.wefactor.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseTest;

/**
 * The Class UserProfileRepositoryTest.
 * 
 * @author Florens Hückstädt
 */
public class UserProfileRepositoryTest extends BaseTest {

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * Basic test.
     */
    @Test
    public final void basicTest() {

        IUserProfile userProfile = new UserProfile("1", "name", "firstName",
                "secondName", "mail@mail.de", "username");
        userProfileRepository.save((UserProfile) userProfile);

    }

}
