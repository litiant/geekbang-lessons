package org.geektimes.projects.user.validator.bean.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PhoneValidAnnotationValidator.class)
public @interface PhoneValid {

    String message() default "{org.geektimes.projects.user.validator.bean.validation.PhoneValid.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
