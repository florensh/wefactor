package de.hhn.labswps.wefactor.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class SimpleSocialUserDetailSevice implements SocialUserDetailsService {

    private UserDetailsService userDetailService;

    public SimpleSocialUserDetailSevice(UserDetailsService userDetailsService) {
        this.userDetailService = userDetailsService;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId)
            throws UsernameNotFoundException, DataAccessException {
        return (SocialUserDetails) userDetailService.loadUserByUsername(userId);
    }

}
