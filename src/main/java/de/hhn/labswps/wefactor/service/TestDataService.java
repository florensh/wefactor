package de.hhn.labswps.wefactor.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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
import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProgrammingLanguage;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;
import de.hhn.labswps.wefactor.web.util.DataUtils;

/**
 * The Class TestDataService creates test data e.g. users, groups, entries.
 */
@Service
public class TestDataService {

    private static final String ENTRY_TEASER = "This is just a simple test entry for demonstration";

    private static final String ENTRY_DESCRIPTION = "This is a test entry";

    /** The Constant ACTIVE_ENVIRONMENT. */
    private static final String ACTIVE_ENVIRONMENT = "dev";

    /** The env. */
    @Autowired
    private Environment env;

    /** The Constant GROUP_DESCRIPTION. */
    private static final String GROUP_DESCRIPTION = "This is a test group";

    /** The Constant PW. */
    private static final String PW = "X";

    /** The Constant THE_GROUP. */
    private static final String THE_GROUP = "The Group";

    /** The group repository. */
    @Autowired
    private GroupRepository groupRepository;

    /** The timeline event repository. */
    @Autowired
    private TimelineEventRepository timelineEventRepository;

    /** The tag repository. */
    @Autowired
    private TagRepository tagRepository;

    /** The entry rating repository. */
    @Autowired
    private EntryRatingRepository entryRatingRepository;

    /** The master entry repository. */
    @Autowired
    private MasterEntryRepository masterEntryRepository;

    /**
     * Generate data.
     *
     * @throws Exception
     *             the exception
     */
    @PostConstruct
    public void generateData() throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains(ACTIVE_ENVIRONMENT)) {
            generateGroups();
            generateUser();
            generateEntries();
            generateProposals();
            generateVersions();
            generateEntryRankings();

        }

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

    /** The account repository. */
    @Autowired
    private AccountRepository accountRepository;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * Make account and store.
     *
     * @param user
     *            the user
     * @param imageUrl
     *            the image url
     */
    private void makeAccountAndStore(String[] user, String imageUrl) {

        Account account = new Account(Role.USER);
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

        ObjectIdentification oid = DataUtils.createObjectIdentification(group,
                Group.class.getSimpleName());
        TimelineEvent event = new TimelineEvent(new Date(),
                profile.getAccount(), group, EventType.USER_JOINED_GROUP, oid);

        this.timelineEventRepository.save(event);

        this.userProfileRepository.save(profile);

    }
}
