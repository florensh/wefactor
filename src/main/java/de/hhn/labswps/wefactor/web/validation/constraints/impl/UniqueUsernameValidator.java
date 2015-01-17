package de.hhn.labswps.wefactor.web.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueUsername;

/**
 * The Class UniqueUsernameValidator.
 */
public class UniqueUsernameValidator implements
ConstraintValidator<UniqueUsername, String> {

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize(final UniqueUsername annotation) {

    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(final String username,
            final ConstraintValidatorContext arg1) {
        boolean isValid = true;
        try {

            if ((username != null) && !username.isEmpty()) {
                final UserProfile up = this.userProfileRepository
                        .findByUsername(username);
                isValid = up == null;
            }

        } catch (final Exception ignore) {
            // ignore
        }
        return isValid;
    }
}
