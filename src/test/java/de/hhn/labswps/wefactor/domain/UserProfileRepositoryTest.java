package de.hhn.labswps.wefactor.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseWebTest;

/**
 * The Class UserProfileRepositoryTest.
 *
 * @author Florens Hückstädt
 */
public class UserProfileRepositoryTest extends BaseWebTest {

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The account repository. */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Basic test.
     */
    @Test
    public final void basicTest() {

        final long currentTime = System.currentTimeMillis();

        final Account account = new Account();
        final UserProfile userProfile = new UserProfile(
                this.accountRepository.save(account), "name", "firstName",
                "secondName", currentTime + "@mail.de", "username_"
                        + currentTime);
        this.userProfileRepository.save(userProfile);

    }

}
