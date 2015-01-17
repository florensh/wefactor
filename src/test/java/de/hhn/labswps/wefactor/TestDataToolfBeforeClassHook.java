package de.hhn.labswps.wefactor;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

/**
 * The Class TestDataToolfBeforeClassHook.
 */
public class TestDataToolfBeforeClassHook extends AbstractTestExecutionListener {

    /**
     * Instantiates a new test data toolf before class hook.
     */
    public TestDataToolfBeforeClassHook() {
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.test.context.support.AbstractTestExecutionListener
     * #beforeTestClass(org.springframework.test.context.TestContext)
     */
    @Override
    public void beforeTestClass(final TestContext testContext) {
        final AccountRepository accountRepository = testContext
                .getApplicationContext().getBean(AccountRepository.class);

        final UserProfileRepository userProfileRepository = testContext
                .getApplicationContext().getBean(UserProfileRepository.class);

        final UserProfile up = userProfileRepository
                .findByUsername("weFactor_testuser");

        if (up == null) {
            final Account account = new Account(Role.USER);
            accountRepository.save(account);
            final UserProfile profile = new UserProfile(account,
                    "mail@mail.de", "weFactor_testuser", "password",
                    ProviderIdentification.WEFACTOR);

            userProfileRepository.save(profile);

        }

    }

    // @Override
    // public void afterTestClass(TestContext testContext) throws Exception {
    //
    // UserProfileRepository userProfileRepository = testContext
    // .getApplicationContext().getBean(UserProfileRepository.class);
    //
    // userProfileRepository.deleteAll();
    //
    // MasterEntryRepository entryRepository =
    // testContext.getApplicationContext()
    // .getBean(MasterEntryRepository.class);
    //
    // entryRepository.deleteAll();
    //
    // AccountRepository accountRepository = testContext
    // .getApplicationContext().getBean(AccountRepository.class);
    //
    // accountRepository.deleteAll();
    //
    // }

}