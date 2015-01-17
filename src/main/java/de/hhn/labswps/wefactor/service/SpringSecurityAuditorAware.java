package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;

/**
 * The weFactor implementation of {@link AuditorAware}.
 */
@Service
public class SpringSecurityAuditorAware implements AuditorAware<Account> {

    /** The account service. */
    @Autowired
    private AccountService accountService;

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
     */
    @Override
    public Account getCurrentAuditor() {
        return accountService.getCurrentAccount();
    }

}
