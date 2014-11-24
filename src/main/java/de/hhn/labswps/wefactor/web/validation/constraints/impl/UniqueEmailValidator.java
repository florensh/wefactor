package de.hhn.labswps.wefactor.web.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueEmail;

public class UniqueEmailValidator implements
        ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public void initialize(UniqueEmail annotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext arg1) {
        boolean isValid = true;
        try {

            if (email != null && !email.isEmpty()) {
                UserProfile up = this.userProfileRepository.findByEmail(email);
                isValid = up == null;
            }

        } catch (final Exception ignore) {
            // ignore
        }
        return isValid;
    }
}