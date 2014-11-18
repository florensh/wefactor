package de.hhn.labswps.wefactor.domain;

import java.util.Date;

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
        UserProfile profile = getTestProfile();

        Entry entry = new Entry();

        entry.setAccount(profile.getAccount());
        entry.setEntryCodeText("bla");
        entry.setEntryDate(new Date());
        entry.setEntryDescription("bla bla");

        entryRepository.save(entry);
        entryRepository.delete(entry);

    }
}
