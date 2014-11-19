package de.hhn.labswps.wefactor;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

public class BeforeClassHook extends AbstractTestExecutionListener {

    public BeforeClassHook() {
    }

    @Override
    public void beforeTestClass(TestContext testContext) {
        AccountRepository accountRepository = testContext
                .getApplicationContext().getBean(AccountRepository.class);

        UserProfileRepository userProfileRepository = testContext
                .getApplicationContext().getBean(UserProfileRepository.class);

        Account account = new Account(Role.USER);
        accountRepository.save(account);
        UserProfile profile = new UserProfile(account, "mail@mail.de",
                "weFactor_testuser", "password",
                ProviderIdentification.WEFACTOR);

        userProfileRepository.save(profile);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

        UserProfileRepository userProfileRepository = testContext
                .getApplicationContext().getBean(UserProfileRepository.class);

        userProfileRepository.deleteAll();

        MasterEntryRepository entryRepository = testContext.getApplicationContext()
                .getBean(MasterEntryRepository.class);

        entryRepository.deleteAll();

        AccountRepository accountRepository = testContext
                .getApplicationContext().getBean(AccountRepository.class);

        accountRepository.deleteAll();

    }

}