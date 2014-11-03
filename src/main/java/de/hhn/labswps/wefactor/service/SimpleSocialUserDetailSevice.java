package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

public class SimpleSocialUserDetailSevice implements SocialUserDetailsService {

    @Autowired
    private UserProfileRepository userRepository;

    public SimpleSocialUserDetailSevice() {
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId)
            throws UsernameNotFoundException, DataAccessException {
        SocialUser securityUser = null;

        UserProfile user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userId
                    + " not found");
        } else {
            securityUser = new SocialUser(user.getUsername(),
                    user.getPassword(), user.getAuthorities());
        }
        return securityUser;
    }

}
