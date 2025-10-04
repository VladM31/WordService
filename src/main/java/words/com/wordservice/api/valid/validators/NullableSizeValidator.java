package words.com.wordservice.api.valid.validators;

import jakarta.validation.ConstraintValidator;
import words.com.wordservice.api.valid.annotation.NullableSize;

public class NullableSizeValidator implements ConstraintValidator<NullableSize, String> {

    private int min;
    private int max;

    @Override
    public void initialize(NullableSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        if (min < 0) {
            throw new IllegalArgumentException("Min value must be greater than 0");
        }
        if(min > max){
            throw new IllegalArgumentException("Min value must be less than max value");
        }
    }

    @Override
    public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
        return value == null || (value.length() >= min && value.length() <= max);
    }
}
