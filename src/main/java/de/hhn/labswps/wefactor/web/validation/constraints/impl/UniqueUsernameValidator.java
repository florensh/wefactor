package de.hhn.labswps.wefactor.web.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueUsername;

public class UniqueUsernameValidator implements
        ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public void initialize(UniqueUsername annotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext arg1) {
        boolean isValid = true;
        try {

            if (username != null && !username.isEmpty()) {
                UserProfile up = this.userProfileRepository
                        .findByUsername(username);
                isValid = up == null;
            }

        } catch (final Exception ignore) {
            // ignore
        }
        return isValid;
    }
}
