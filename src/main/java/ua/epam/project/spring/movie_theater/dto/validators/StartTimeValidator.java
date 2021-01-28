package ua.epam.project.spring.movie_theater.dto.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class StartTimeValidator implements
        ConstraintValidator<StartTimeConstraint, LocalTime> {

    @Override
    public void initialize(StartTimeConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalTime time, ConstraintValidatorContext constraintValidatorContext) {
        return time.getHour() >= 9 && time.getHour() <= 22;
    }
}
