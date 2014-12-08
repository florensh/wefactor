package de.hhn.labswps.wefactor.testdata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import de.hhn.labswps.wefactor.BaseTest;
import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

// @ActiveProfiles("test")
@ActiveProfiles("dev")
public class TestDataTool extends BaseTest {

    @Test
    public void generateData() throws Exception {
        clearData();

        generateUser();
        generateEntries();
        generateProposals();
        generateVersions();
        generateEntryRankings();

    }

    private void clearData() {
        // TODO Auto-generated method stub

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
        this.accountRepository.save(account);

        UserProfile profile = new UserProfile();
        profile.setAccount(account);

        profile = new UserProfile(account, user[0] + " " + user[1], user[0],
                user[1], user[2], user[2]);

        profile.setProviderId(ProviderIdentification.WEFACTOR.name());
        profile.setImageUrl(WeFactorValues.DEFAULT_IMAGE_URL);

        this.userProfileRepository.save(profile);

    }
}
