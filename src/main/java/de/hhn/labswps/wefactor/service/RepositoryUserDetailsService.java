package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

public class RepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserProfileRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User securityUser = null;

        UserProfile user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userName
                    + " not found");
        } else {
            securityUser = new User(user.getUsername(), user.getPassword(),
                    user.getAccount().getAuthorities());
        }
        return securityUser;
    }

    public UserDetails loadUserByUserId(String userId) {
        User securityUser = null;

        // UserProfile user = userRepository.findByUserId(userId);
        UserProfile user = userRepository.findOne(Long.parseLong(userId));

        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userId
                    + " not found");
        } else {
            securityUser = new User(user.getUsername(), user.getPassword(),
                    user.getAccount().getAuthorities());
        }
        return securityUser;

    }

}
