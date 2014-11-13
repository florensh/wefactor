package de.hhn.labswps.wefactor.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseTest;

/**
 * The Class EntryRepositoryTest.
 */
public class EntryRepositoryTest extends BaseTest {

    /** The entry repository. */
    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    /**
     * Basic test.
     */
    @Test
    public final void basicTest() {

        // Account account = new Account();
        // accountRepository.save(account);
        // UserProfile profile = new UserProfile(account, "hallo@gmx.de", "max",
        // "12345678");
        //
        // userProfileRepository.save(profile);
        //
        // Entry entry = new Entry();
        //
        // entry.setAccount(account);
        // entry.setEntryCodeText("bla");
        // entry.setEntryDate(new Date());
        // entry.setEntryDescription("bla bla");
        //
        // entryRepository.save(entry);

    }
}
