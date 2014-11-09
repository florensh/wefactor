package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;

@Service
public class SpringSecurityAuditorAware implements AuditorAware<Account> {

    @Autowired
    private AccountService accountService;

    @Override
    public Account getCurrentAuditor() {
        return accountService.getCurrentAccount();
    }

}
