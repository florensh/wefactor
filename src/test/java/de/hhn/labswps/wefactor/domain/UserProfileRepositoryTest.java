package de.hhn.labswps.wefactor.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseTest;

/**
 * The Class UserProfileRepositoryTest.
 * 
 * @author Florens H�ckst�dt
 */
public class UserProfileRepositoryTest extends BaseTest {

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Basic test.
     */
    @Test
    public final void basicTest() {

        // Account account = new Account();
        // UserProfile userProfile = new UserProfile(
        // accountRepository.save(account), "name", "firstName",
        // "secondName", "mail@mail.de", "username");
        // userProfileRepository.save((UserProfile) userProfile);

    }

}
