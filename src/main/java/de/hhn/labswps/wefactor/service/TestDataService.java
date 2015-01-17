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

@Service
public class TestDataService {

    @Autowired
    Environment env;

    private static final String GROUP_DESCRIPTION = "This is a test group";

    private static final String PW = "X";

    private static final String THE_GROUP = "The Group";

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private EntryRatingRepository entryRatingRepository;

    @Autowired
    private MasterEntryRepository masterEntryRepository;

    @PostConstruct
    public void generateData() throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            generateGroups();
            generateUser();
            generateEntries();
            generateProposals();
            generateVersions();
            generateEntryRankings();

        }

    }

    private void generateGroups() {
        Group group = new Group();
        group.setName(THE_GROUP);
        group.setDescription(GROUP_DESCRIPTION);
        group.setImageUrl(WeFactorValues.DEFAULT_GROUP_IMAGE_URL);
        this.groupRepository.save(group);

    }

    private void generateEntryRankings() {
        // TODO Auto-generated method stub

    }

    private void generateVersions() {
        // TODO Auto-generated method stub

    }

    private void generateProposals() {
        // TODO Auto-generated method stub

    }

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
            e.setEntryDescription("bla bla");
            e.setEntryCodeText("sf");
            e.setEntryDate(new Date(System.currentTimeMillis()));
            e.setAccount(up.getAccount());
            e.setLanguage(lang.getMode());
            e.setTeaser("This is just a simple test entry for demonstration");
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

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

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
