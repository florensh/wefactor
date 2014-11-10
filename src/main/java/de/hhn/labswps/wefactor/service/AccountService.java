package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

@Service
public class AccountService {

    @Autowired
    UserProfileRepository userProfileRepository;

    public Account getCurrentAccount() {
        Account account = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();

        User socialUser = (User) securityContext.getAuthentication()
                .getPrincipal();

        UserProfile up = this.userProfileRepository.findByUsername(socialUser
                .getUsername());
        account = up.getAccount();

        return account;
    }

}
