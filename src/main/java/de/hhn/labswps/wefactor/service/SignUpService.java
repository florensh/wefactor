package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

@Service
public class SignUpService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(String username, String email, String password) {
        if (username == null || email == null || password == null) {
            throw new IllegalArgumentException();
        }

        Account account = this.accountRepository.save(new Account());

        UserProfile profile = new UserProfile(account, email, username,
                passwordEncoder.encode(password));
        profile = this.userProfileRepository.save(profile);
        return profile.getUserId();

    }

}
