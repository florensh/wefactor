package de.hhn.labswps.wefactor.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

public class AccountConnectionSignUpService implements ConnectionSignUp {

    private static final Logger LOG = LoggerFactory
            .getLogger(AccountConnectionSignUpService.class);

    public AccountConnectionSignUpService(UserProfileRepository repository,
            AccountRepository accountRepository) {
        this.userProfileRepository = repository;
        this.accountRepository = accountRepository;
    }

    private UserProfileRepository userProfileRepository;

    private AccountRepository accountRepository;

    public String execute(Connection<?> connection) {
        org.springframework.social.connect.UserProfile profile = connection
                .fetchUserProfile();

        String userId = UUID.randomUUID().toString();
        // TODO: Or simply use: r = new Random(); r.nextInt(); ???
        LOG.debug("Created user-id: " + userId);

        UserProfile userProfile = new UserProfile(
                accountRepository.save(new Account()), userId, profile);

        userProfileRepository.save(userProfile);
        return userId;
    }

}
