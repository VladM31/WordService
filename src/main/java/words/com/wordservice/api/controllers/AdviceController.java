package words.com.wordservice.api.controllers;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import words.com.wordservice.api.responds.ErrorDetailsRespond;
import words.com.wordservice.api.responds.ErrorRespond;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestControllerAdvice
@ResponseBody
public class AdviceController {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ErrorRespond handleValidationException(BindException ex) {
        BindingResult result = ex.getBindingResult();
        var message = result.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));

        if (message.isBlank()){
            message = ex.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
        }

        return new ErrorRespond(
                new ErrorDetailsRespond(
                        message,
                        ex.getClass().getSimpleName()
                )
        );
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class})
    public ErrorRespond handleValidationException(ValidationException e) {
        log.error("Validation error", e);
        var message = Optional.ofNullable(e.getCause())
                .map(Throwable::getMessage)
                .orElseGet(e::getMessage);
        var simpleName = Optional.ofNullable(e.getCause())
                .map(Throwable::getClass)
                .map(Class::getSimpleName)
                .orElseGet(() -> e.getClass().getSimpleName());


        return new ErrorRespond(
                new ErrorDetailsRespond(
                        message,
                        simpleName
                )
        );
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HandlerMethodValidationException.class})
    public ErrorRespond handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        log.error("Validation error", e);
        var errors = e.getDetailMessageArguments();
        var message =  Stream.of(errors).map(Object::toString).collect(Collectors.joining("\n"));

        return new ErrorRespond(
                new ErrorDetailsRespond(
                        e.getMessage() + "\n" + message,
                        e.getClass().getSimpleName()
                )
        );
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {Exception.class})
    public ErrorRespond handleException(Exception e) {
        log.error("Exception occurred: {}", e.getMessage(), e);
        return new ErrorRespond(
                new ErrorDetailsRespond(
                        e.getMessage(),
                        e.getClass().getSimpleName()
                )
        );
    }

}
