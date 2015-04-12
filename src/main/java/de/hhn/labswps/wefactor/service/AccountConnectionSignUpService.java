package de.hhn.labswps.wefactor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

/**
 * The weFactor implementation of {@link ConnectionSignUp}.
 */
public class AccountConnectionSignUpService implements ConnectionSignUp {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(AccountConnectionSignUpService.class);

    /**
     * Instantiates a new account connection sign up service.
     *
     * @param repository
     *            the repository
     * @param accountRepository
     *            the account repository
     */
    public AccountConnectionSignUpService(UserProfileRepository repository,
            AccountRepository accountRepository, JournalService journalService) {
        this.userProfileRepository = repository;
        this.accountRepository = accountRepository;
        this.journalService = journalService;
    }

    /** The user profile repository. */
    private UserProfileRepository userProfileRepository;

    /** The account repository. */
    private AccountRepository accountRepository;

    private JournalService journalService;

    /*
     * (non-Javadoc)
     * @see org.springframework.social.connect.ConnectionSignUp#execute(org.
     * springframework.social.connect.Connection)
     */
    public String execute(Connection<?> connection) {
        org.springframework.social.connect.UserProfile profile = connection
                .fetchUserProfile();

        UserProfile userProfile = new UserProfile(
                accountRepository.save(new Account(Role.ROLE_USER)), profile,
                connection.getImageUrl(),
                ProviderIdentification.valueOf(connection.getKey()
                        .getProviderId().toUpperCase()));

        userProfile = userProfileRepository.save(userProfile);
        this.journalService.writeEntry(profile.getUsername(),
                EventType.REGISTER);
        return userProfile.getUserId();
    }

}
