package ru.geekbrains.gym.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.exceptions.AppException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT);
        try {
            Date dateToCheck = formatter.parse(value);
            Date now = new Date();
            return dateToCheck.after(now);

        } catch (ParseException e) {
            throw new AppException("Date format exception", 400);
        }
    }
}
