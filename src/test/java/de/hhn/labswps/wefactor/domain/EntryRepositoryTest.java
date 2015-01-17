package de.hhn.labswps.wefactor.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseWebTest;

/**
 * The Class EntryRepositoryTest.
 */
public class EntryRepositoryTest extends BaseWebTest {

    /** The entry repository. */
    @Autowired
    private MasterEntryRepository masterEntryRepository;

    /** The version entry repository. */
    @Autowired
    private VersionEntryRepository versionEntryRepository;

    /** The account repository. */
    @Autowired
    AccountRepository accountRepository;

    /** The user profile repository. */
    @Autowired
    UserProfileRepository userProfileRepository;

    /**
     * Basic test.
     */
    @Test
    public final void basicTest() {
        final UserProfile profile = this.getTestProfile();

        final MasterEntry masterEntry = new MasterEntry();

        masterEntry.setAccount(profile.getAccount());
        masterEntry.setEntryCodeText("bla");
        masterEntry.setEntryDate(new Date());
        masterEntry.setEntryDescription("bla bla");
        this.masterEntryRepository.save(masterEntry);

        this.masterEntryRepository.delete(masterEntry);

    }

    /**
     * Test new version.
     */
    @Test
    public final void testNewVersion() {
        final UserProfile profile = this.getTestProfile();

        MasterEntry masterEntry = new MasterEntry();

        masterEntry.setAccount(profile.getAccount());
        masterEntry.setEntryCodeText("bla");
        masterEntry.setEntryDate(new Date());
        masterEntry.setEntryDescription("bla bla");
        this.masterEntryRepository.save(masterEntry);

        final VersionEntry versionEntry = new VersionEntry();

        versionEntry.setAccount(profile.getAccount());
        versionEntry.setEntryCodeText("bla");
        versionEntry.setEntryDate(new Date());
        versionEntry.setEntryDescription("bla bla");

        this.versionEntryRepository.save(versionEntry);
        masterEntry.addVersion(versionEntry);

        masterEntry = this.masterEntryRepository.save(masterEntry);

        assertThat(masterEntry.getVersions(), not(empty()));

        this.versionEntryRepository.delete(versionEntry);
        this.masterEntryRepository.delete(masterEntry);

    }
}
