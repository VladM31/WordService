package words.com.wordservice.api.valid.validators;

import jakarta.validation.ConstraintValidator;
import words.com.wordservice.api.valid.annotation.StringIn;

import java.util.Objects;

public class StringInValidator implements ConstraintValidator<StringIn, String> {

        private String[] values;
        private boolean canBeNull;

        @Override
        public void initialize(StringIn constraintAnnotation) {
            values = constraintAnnotation.values();
            canBeNull = constraintAnnotation.canBeNull();
        }

        @Override
        public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
            for (String s : values) {
                if (Objects.equals(s,value)) {
                    return true;
                }
            }
            return value == null && canBeNull;
        }
}
