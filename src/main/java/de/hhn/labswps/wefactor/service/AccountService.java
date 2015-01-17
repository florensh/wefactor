package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;
import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

/**
 * The Class AccountService gives access to the account of the user who is
 * currently logged in.
 */
@Service
public class AccountService {

    /** The user profile repository. */
    @Autowired
    UserProfileRepository userProfileRepository;

    /** The account repository. */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Gets the current account.
     *
     * @return the current account
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account getCurrentAccount() {
        Account account = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication a = securityContext.getAuthentication();

        if (a != null) {
            User user = (User) a.getPrincipal();

            UserProfile up = this.userProfileRepository.findByUsername(user
                    .getUsername());
            account = up.getAccount();

        } else {
            UserProfile up = this.userProfileRepository
                    .findByUsername("SYSTEM");

            if (up == null) {
                Account acc = new Account(Role.USER);
                this.accountRepository.save(acc);
                up = new UserProfile(acc, "", "SYSTEM", "",
                        ProviderIdentification.WEFACTOR);
                this.userProfileRepository.save(up);
            }

            account = up.getAccount();
        }

        return account;
    }

}
