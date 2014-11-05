package de.hhn.labswps.wefactor.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

@Service
public class SignUpService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(String username, String email, String password) {
        if (username == null || email == null || password == null) {
            throw new IllegalArgumentException();
        }

        String userId = UUID.randomUUID().toString();

        UserProfile profile = new UserProfile(userId, email, username,
                passwordEncoder.encode(password));
        this.userProfileRepository.save(profile);
        return userId;

    }

}