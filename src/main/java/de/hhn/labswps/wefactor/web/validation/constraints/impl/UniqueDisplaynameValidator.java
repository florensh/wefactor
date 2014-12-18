package de.hhn.labswps.wefactor.web.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueDisplayname;

public class UniqueDisplaynameValidator implements
        ConstraintValidator<UniqueDisplayname, String> {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public void initialize(UniqueDisplayname annotation) {

    }

    @Override
    public boolean isValid(String displayname, ConstraintValidatorContext arg1) {
        boolean isValid = true;
        try {

            if (displayname != null && !displayname.isEmpty()) {
                UserProfile up = this.userProfileRepository
                        .findByName(displayname);
                isValid = up == null;
            }

        } catch (final Exception ignore) {
            // ignore
        }
        return isValid;
    }
}
