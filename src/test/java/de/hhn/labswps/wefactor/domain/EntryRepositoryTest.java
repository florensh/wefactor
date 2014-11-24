package de.hhn.labswps.wefactor.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

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
    private MasterEntryRepository masterEntryRepository;

    @Autowired
    private VersionEntryRepository versionEntryRepository;

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

        MasterEntry masterEntry = new MasterEntry();

        masterEntry.setAccount(profile.getAccount());
        masterEntry.setEntryCodeText("bla");
        masterEntry.setEntryDate(new Date());
        masterEntry.setEntryDescription("bla bla");
        masterEntryRepository.save(masterEntry);

        masterEntryRepository.delete(masterEntry);

    }

    @Test
    public final void testNewVersion() {
        UserProfile profile = getTestProfile();

        MasterEntry masterEntry = new MasterEntry();

        masterEntry.setAccount(profile.getAccount());
        masterEntry.setEntryCodeText("bla");
        masterEntry.setEntryDate(new Date());
        masterEntry.setEntryDescription("bla bla");
        masterEntryRepository.save(masterEntry);

        VersionEntry versionEntry = new VersionEntry();

        versionEntry.setAccount(profile.getAccount());
        versionEntry.setEntryCodeText("bla");
        versionEntry.setEntryDate(new Date());
        versionEntry.setEntryDescription("bla bla");

        versionEntryRepository.save(versionEntry);
        masterEntry.addVersion(versionEntry);

        masterEntry = masterEntryRepository.save(masterEntry);

        assertThat(masterEntry.getVersions(), not(empty()));

        versionEntryRepository.delete(versionEntry);
        masterEntryRepository.delete(masterEntry);

    }
}
