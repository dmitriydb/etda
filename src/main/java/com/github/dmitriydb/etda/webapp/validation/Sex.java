package com.github.dmitriydb.etda.webapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SexValidator.class)
@Documented
public @interface Sex
{

    String message() default "{customValidator.sex";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ FIELD, METHOD, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        Sex[] value();
    }
}