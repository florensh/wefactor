package de.hhn.labswps.wefactor.web.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import de.hhn.labswps.wefactor.web.validation.constraints.FieldMatch;

/**
 * The Class FieldMatchValidator.
 */
public class FieldMatchValidator implements
ConstraintValidator<FieldMatch, Object> {

    /** The first field name. */
    private String firstFieldName;

    /** The second field name. */
    private String secondFieldName;

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(final Object value,
            final ConstraintValidatorContext context) {
        try {
            final Object firstObj = PropertyUtils.getProperty(value,
                    this.firstFieldName);
            final Object secondObj = PropertyUtils.getProperty(value,
                    this.secondFieldName);

            return ((firstObj == null) && (secondObj == null))
                    || ((firstObj != null) && firstObj.equals(secondObj));
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }
}