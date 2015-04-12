package de.hhn.labswps.wefactor;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

/**
 * The Class BeforeClassHook.
 */
public class BeforeClassHook extends AbstractTestExecutionListener {

    /**
     * Instantiates a new before class hook.
     */
    public BeforeClassHook() {
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

        final Account account = new Account(Role.ROLE_USER);
        accountRepository.save(account);
        final UserProfile profile = new UserProfile(account, "mail@mail.de",
                "weFactor_testuser", "password",
                ProviderIdentification.WEFACTOR);
        profile.setImageUrl(WeFactorValues.DEFAULT_IMAGE_URL);

        userProfileRepository.save(profile);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.test.context.support.AbstractTestExecutionListener
     * #afterTestClass(org.springframework.test.context.TestContext)
     */
    @Override
    public void afterTestClass(final TestContext testContext) throws Exception {

        final UserProfileRepository userProfileRepository = testContext
                .getApplicationContext().getBean(UserProfileRepository.class);

        userProfileRepository.deleteAll();

        final MasterEntryRepository entryRepository = testContext
                .getApplicationContext().getBean(MasterEntryRepository.class);

        entryRepository.deleteAll();

        final AccountRepository accountRepository = testContext
                .getApplicationContext().getBean(AccountRepository.class);

        accountRepository.deleteAll();

    }

}