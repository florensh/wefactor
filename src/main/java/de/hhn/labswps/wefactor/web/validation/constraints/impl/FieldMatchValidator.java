package de.hhn.labswps.wefactor.web.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import de.hhn.labswps.wefactor.web.validation.constraints.FieldMatch;

public class FieldMatchValidator implements
        ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value,
            final ConstraintValidatorContext context) {
        try {
            final Object firstObj = PropertyUtils.getProperty(value,
                    firstFieldName);
            final Object secondObj = PropertyUtils.getProperty(value,
                    secondFieldName);

            return firstObj == null && secondObj == null || firstObj != null
                    && firstObj.equals(secondObj);
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }
}