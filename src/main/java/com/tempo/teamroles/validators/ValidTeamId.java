package com.tempo.teamroles.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidTeamValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
public @interface ValidTeamId {
    String message() default "Team ID must exist in Team Service";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
