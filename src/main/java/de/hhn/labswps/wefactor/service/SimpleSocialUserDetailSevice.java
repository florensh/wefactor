package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

/**
 * The weFactor implementation of {@link SocialUserDetailsService}.
 */
public class SimpleSocialUserDetailSevice implements SocialUserDetailsService {

    /** The user repository. */
    @Autowired
    private UserProfileRepository userRepository;

    /**
     * Instantiates a new simple social user detail sevice.
     */
    public SimpleSocialUserDetailSevice() {
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.security.SocialUserDetailsService#loadUserByUserId
     * (java.lang.String)
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId)
            throws UsernameNotFoundException, DataAccessException {
        SocialUser securityUser = null;

        UserProfile user = userRepository.findOne(Long.parseLong(userId));

        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userId
                    + " not found");
        } else {
            securityUser = new SocialUser(user.getUsername(),
                    user.getPassword(), user.getAccount().getAuthorities());
        }
        return securityUser;
    }

}
