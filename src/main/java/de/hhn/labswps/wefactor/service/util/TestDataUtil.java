package de.hhn.labswps.wefactor.service.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.EntryRating;
import de.hhn.labswps.wefactor.domain.EntryRatingRepository;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.MasterEntry;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;
import de.hhn.labswps.wefactor.domain.Tag;
import de.hhn.labswps.wefactor.domain.TagRepository;
import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.domain.VersionEntry;
import de.hhn.labswps.wefactor.domain.VersionEntryRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProgrammingLanguage;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;
import de.hhn.labswps.wefactor.web.util.DataUtils;

public class TestDataUtil {

    private static final String ENTRY_TEASER = "This is just a simple test entry for demonstration";

    private static final String ENTRY_DESCRIPTION = "This is a test entry";

    /** The Constant ACTIVE_ENVIRONMENT. */
    private static final String ACTIVE_ENVIRONMENT = "dev";

    /** The Constant GROUP_DESCRIPTION. */
    private static final String GROUP_DESCRIPTION = "This is a test group";

    /** The Constant PW. */
    private static final String PW = "X";

    /** The Constant THE_GROUP. */
    private static final String THE_GROUP = "The Group";

    /** The group repository. */
    private GroupRepository groupRepository;

    /** The timeline event repository. */
    private TimelineEventRepository timelineEventRepository;

    /** The tag repository. */
    private TagRepository tagRepository;

    /** The entry rating repository. */
    private EntryRatingRepository entryRatingRepository;

    /** The master entry repository. */
    private MasterEntryRepository masterEntryRepository;

    private VersionEntryRepository versionEntryRepository;

    /** The account repository. */
    private AccountRepository accountRepository;

    /** The user profile repository. */
    private UserProfileRepository userProfileRepository;

    public TestDataUtil(AccountRepository accountRepository,
            EntryRatingRepository entryRatingRepository,
            GroupRepository groupRepository,
            MasterEntryRepository masterEntryRepository,
            TagRepository tagRepository,
            UserProfileRepository userProfileRepository,
            TimelineEventRepository timelineEventRepository,
            VersionEntryRepository versionEntryRepository) {

        this.groupRepository = groupRepository;
        this.timelineEventRepository = timelineEventRepository;
        this.tagRepository = tagRepository;
        this.entryRatingRepository = entryRatingRepository;
        this.masterEntryRepository = masterEntryRepository;
        this.accountRepository = accountRepository;
        this.userProfileRepository = userProfileRepository;
        this.versionEntryRepository = versionEntryRepository;

    }

    /**
     * Generate data.
     *
     * @throws Exception
     *             the exception
     */
    public void generateData() throws Exception {

        generateGroups();
        generateUser();
        // generateEntries();
        // generateProposals();
        // generateVersions();
        // generateEntryRankings();
        generateTestEntryForDiff();

    }

    private void generateTestEntryForDiff() {
        List<UserProfile> allUser = this.userProfileRepository
                .findByPassword(PW);

        UserProfile up = allUser.iterator().next();

        Tag tag1 = new Tag();
        tag1.setName("DiffTest");
        tag1 = this.tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("JavaFX");
        tag2 = this.tagRepository.save(tag2);

        MasterEntry e = new MasterEntry();
        e.setName("Asynchrone Aufrufe  in JavaFX (Diff Test)");
        e.setEntryDescription("<p>Hier ein Beispiel, wie man die Manipulation von Attributen im GVS-Projekt &uuml;ber einen asynchronen Aufrufmechanismus abhandeln kann. Die hier dargestellte Task ist f&uuml;r mehrere Aufrufe, n&auml;mlich f&uuml;r alle Aufrufe, die sich mit dem Manipulieren von Attributen besch&auml;ftigt, konfiguriert. Die Aufrufe selbst werden &uuml;ber eine \"Kernel\"-Klasse durchgef&uuml;hrt. Das Aufrufergebnis wird einem \"Identity\"-Objekt &uuml;bergeben, welches dann von der GUI visualisiert wird.</p>");

        String content = null;
        try {
            FileInputStream stream = new FileInputStream(
                    "src/main/resources/testdata/DiffTest.txt");
            content = IOUtils.toString(stream, "utf-8");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        e.setEntryCodeText(content);
        e.setEntryDate(new Date(System.currentTimeMillis() - 1000));
        e.setAccount(up.getAccount());
        e.setLanguage(WeFactorValues.ProgrammingLanguage.Java.getMode());
        e.setTeaser("Auch in JavaFX sollte man Aufrufe unbestimmter Laufzeit in separate Threads auslagern. Dazu bietet JavaFX die Verwendung der Klasse Task an...");
        e.addTag(tag1);
        e.addTag(tag2);
        this.masterEntryRepository.save(e);

        VersionEntry version = new VersionEntry();
        version.setMasterOfVersion(e);
        version.setAccount(e.getAccount());
        version.setChanges(e.getChanges());

        try {
            FileInputStream stream = new FileInputStream(
                    "src/main/resources/testdata/DiffTest_Version.txt");
            content = IOUtils.toString(stream, "utf-8");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        version.setEntryCodeText(content);
        version.setEntryDate(new Date(System.currentTimeMillis()));
        version.setEntryDescription(e.getEntryDescription());
        version.setLanguage(e.getLanguage());
        version.setName(e.getName());
        version.setTeaser(e.getTeaser());
        version.setChanges("some changes to test the diff view!");

        if (!e.getTags().isEmpty()) {
            for (final Tag tag : e.getTags()) {
                version.addTag(tag);
            }
        }

        this.versionEntryRepository.save(version);

    }

    /**
     * Generate groups.
     */
    private void generateGroups() {
        Group group = new Group();
        group.setName(THE_GROUP);
        group.setDescription(GROUP_DESCRIPTION);
        group.setImageUrl(WeFactorValues.DEFAULT_GROUP_IMAGE_URL);
        this.groupRepository.save(group);

    }

    /**
     * Generate entry rankings.
     */
    private void generateEntryRankings() {
        // TODO Auto-generated method stub

    }

    /**
     * Generate versions.
     */
    private void generateVersions() {
        // TODO Auto-generated method stub

    }

    /**
     * Generate proposals.
     */
    private void generateProposals() {
        // TODO Auto-generated method stub

    }

    /**
     * Generate entries.
     */
    private void generateEntries() {

        List<UserProfile> allUser = this.userProfileRepository
                .findByPassword(PW);

        Tag tag1 = new Tag();
        tag1.setName("Hello World");
        tag1 = this.tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("Demo");
        tag2 = this.tagRepository.save(tag2);

        for (UserProfile up : allUser) {
            ProgrammingLanguage[] langs = WeFactorValues.ProgrammingLanguage
                    .values();

            ProgrammingLanguage lang = langs[(int) (Math.random()
                    * langs.length - 1)];
            MasterEntry e = new MasterEntry();
            e.setName("Hello World in " + lang.getDisplayName());
            e.setEntryDescription(ENTRY_DESCRIPTION);
            e.setEntryCodeText("sf");
            e.setEntryDate(new Date(System.currentTimeMillis()));
            e.setAccount(up.getAccount());
            e.setLanguage(lang.getMode());
            e.setTeaser(ENTRY_TEASER);
            EntryRating rating = new EntryRating();
            rating.setAccount(up.getAccount());
            rating.setValue((int) (Math.random() * 5));
            e.addRating(rating);
            e.addTag(tag1);
            e.addTag(tag2);
            this.masterEntryRepository.save(e);
            this.entryRatingRepository.save(rating);

        }

    }

    /**
     * Generate user.
     */
    private void generateUser() {
        String csvFile = "V:\\git\\wefactor\\src\\main\\resources\\users.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int i = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] user = line.split(cvsSplitBy);

                System.out.println("user [firstname= " + user[0] + " , name="
                        + user[1] + "]");

                String imageUrl = "http://api.randomuser.me/portraits/med/men/"
                        + i + ".jpg";
                i++;

                makeAccountAndStore(user, imageUrl);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");

    }

    /**
     * Make account and store.
     *
     * @param user
     *            the user
     * @param imageUrl
     *            the image url
     */
    private void makeAccountAndStore(String[] user, String imageUrl) {

        Account account = new Account(Role.ROLE_USER);
        Group group = this.groupRepository.findByNameContaining(THE_GROUP).get(
                0);
        this.accountRepository.save(account);
        group.addMember(account);
        this.accountRepository.save(account);
        this.groupRepository.save(group);

        UserProfile profile = new UserProfile();
        profile.setAccount(account);

        profile = new UserProfile(account, user[0] + " " + user[1], user[0],
                user[1], user[2], user[2]);

        profile.setProviderId(ProviderIdentification.WEFACTOR.name());

        profile.setImageUrl(imageUrl);
        profile.setPassword(PW);

        this.userProfileRepository.save(profile);

        ObjectIdentification oid = DataUtils.createObjectIdentification(group,
                Group.class.getSimpleName());
        TimelineEvent event = new TimelineEvent(new Date(),
                profile.getAccount(), group, EventType.USER_JOINED_GROUP, oid);

        this.timelineEventRepository.save(event);

        this.userProfileRepository.save(profile);

    }
}
