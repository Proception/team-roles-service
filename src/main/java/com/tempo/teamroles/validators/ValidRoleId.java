package com.tempo.teamroles.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidRoleValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface ValidRoleId {
    String message() default "Role ID does exist or has not been created";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
