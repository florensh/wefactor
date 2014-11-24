package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

@Service
public class SignUpService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        return profile.getUserId();

    }

}
