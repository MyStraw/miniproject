package annotation.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SaisaiValidator implements ConstraintValidator<Saisai, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(Saisai constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value >= min && value <= max;
    }
}
