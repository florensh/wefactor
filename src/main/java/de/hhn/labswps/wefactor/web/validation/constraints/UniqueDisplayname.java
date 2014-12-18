package de.hhn.labswps.wefactor.web.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import de.hhn.labswps.wefactor.web.validation.constraints.impl.UniqueDisplaynameValidator;

@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueDisplaynameValidator.class)
@Documented
public @interface UniqueDisplayname {
    String message() default "{com.mycompany.constraints.checkcase}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
