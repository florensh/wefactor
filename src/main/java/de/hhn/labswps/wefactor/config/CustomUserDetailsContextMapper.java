package de.hhn.labswps.wefactor.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import de.hhn.labswps.wefactor.service.SignUpService;

public class CustomUserDetailsContextMapper implements UserDetailsContextMapper {

    @Autowired
    private UserDetailsService repositoryUserDetailsService;

    @Autowired
    private SignUpService signUpService;

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx,
            String username, Collection<? extends GrantedAuthority> authorities) {

        UserDetails ud;

        String firstName = ctx.getStringAttribute("givenName");
        String lastName = ctx.getStringAttribute("sn");
        String email = ctx.getStringAttribute("mailAlternateAddress");

        try {
            ud = repositoryUserDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            signUpService.execute(username, email, firstName, lastName);
            ud = repositoryUserDetailsService.loadUserByUsername(username);
        }

        return ud;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

    }

}
