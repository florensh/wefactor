package de.hhn.labswps.wefactor.testdata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithSecurityContextTestExcecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import de.hhn.labswps.wefactor.BaseTest;
import de.hhn.labswps.wefactor.TestDataToolfBeforeClassHook;
import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

@ActiveProfiles("dev")
@WebAppConfiguration
@TestExecutionListeners(listeners = { ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        TestDataToolfBeforeClassHook.class,
        WithSecurityContextTestExcecutionListener.class })
@WithUserDetails("weFactor_testuser")
// @Transactional
public class TestDataTool extends BaseTest {

    private static final String PW = "X";

    private static final String THE_GROUP = "The Group";

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void generateData() throws Exception {
        clearData();

        generateGroups();
        // generateUser();
        generateEntries();
        generateProposals();
        generateVersions();
        generateEntryRankings();

    }

    private void generateGroups() {
        Group group = new Group();
        group.setName(THE_GROUP);
        group.setDescription("bla bla bla");
        group.setImageUrl(WeFactorValues.DEFAULT_GROUP_IMAGE_URL);
        this.groupRepository.save(group);

    }

    private void clearData() {
        this.userProfileRepository.deleteAll();
        List<UserProfile> userProfiles = this.userProfileRepository
                .findByPassword("");

        for (UserProfile up : userProfiles) {
            Account account = up.getAccount();
            this.userProfileRepository.delete(up);
            this.accountRepository.delete(account);
        }

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
        // TODO Auto-generated method stub

    }

    private void generateUser() {
        String csvFile = "V:\\git\\wefactor\\src\\main\\resources\\users.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] user = line.split(cvsSplitBy);

                System.out.println("user [firstname= " + user[0] + " , name="
                        + user[1] + "]");

                makeAccountAndStore(user);

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

    private void makeAccountAndStore(String[] user) {

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
        profile.setImageUrl(WeFactorValues.DEFAULT_IMAGE_URL);
        profile.setPassword(PW);

        this.userProfileRepository.save(profile);

    }
}
