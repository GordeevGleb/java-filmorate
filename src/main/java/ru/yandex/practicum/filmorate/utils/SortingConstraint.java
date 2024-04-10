package ru.yandex.practicum.filmorate.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SortingValidator.class)
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface SortingConstraint {

    String message() default "Path variable must be 'year' or 'likes'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
