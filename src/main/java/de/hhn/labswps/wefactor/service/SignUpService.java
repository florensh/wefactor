package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.JournalEntry.EventType;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

/**
 * The Class SignUpService.
 */
@Service
public class SignUpService extends BaseSignUpService {

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The account repository. */
    @Autowired
    private AccountRepository accountRepository;

    /** The password encoder. */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Execute.
     *
     * @param username
     *            the username
     * @param email
     *            the email
     * @param password
     *            the password
     * @return the string
     */
    public String execute(final String username, final String email,
            final String password) {
        if ((username == null) || (email == null) || (password == null)) {
            throw new IllegalArgumentException();
        }

        final Account account = this.accountRepository.save(new Account(
                Role.USER));

        UserProfile profile = new UserProfile(account, email, username,
                this.passwordEncoder.encode(password),
                ProviderIdentification.WEFACTOR);
        profile = this.userProfileRepository.save(profile);
        writeEventToJournal(profile.getUsername(), EventType.REGISTER);
        return profile.getUserId();

    }

    public String execute(final String username, final String email,
            final String firstname, final String lastname) {
        if ((username == null)
        // || (email == null)

        ) {
            throw new IllegalArgumentException();
        }

        final Account account = this.accountRepository.save(new Account(
                Role.USER));

        UserProfile profile = new UserProfile(account, null, firstname,
                lastname, email, username, "nopasswordforldapuser",
                ProviderIdentification.LDAP);

        profile = this.userProfileRepository.save(profile);
        writeEventToJournal(profile.getUsername(), EventType.REGISTER);
        return profile.getUserId();

    }
}
