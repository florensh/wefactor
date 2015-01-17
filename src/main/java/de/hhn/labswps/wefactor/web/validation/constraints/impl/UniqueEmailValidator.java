package de.hhn.labswps.wefactor.web.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueEmail;

/**
 * The Class UniqueEmailValidator.
 */
public class UniqueEmailValidator implements
ConstraintValidator<UniqueEmail, String> {

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
    public void initialize(final UniqueEmail annotation) {

    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(final String email,
            final ConstraintValidatorContext arg1) {
        boolean isValid = true;
        try {

            if ((email != null) && !email.isEmpty()) {
                final UserProfile up = this.userProfileRepository
                        .findByEmail(email);
                isValid = up == null;
            }

        } catch (final Exception ignore) {
            // ignore
        }
        return isValid;
    }
}