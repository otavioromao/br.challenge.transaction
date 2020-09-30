//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.exception.handling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is responsible for handle the exceptions from the controller.
 */
@RestControllerAdvice
public class EventErrorHandler
{
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorMessage> handle(MethodArgumentNotValidException exception)
    {
	List<ErrorMessage> errorMessages = new ArrayList<>();

	exception.getBindingResult().getFieldErrors().forEach(error ->
	{
	    String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

	    errorMessages.add(new ErrorMessage(HttpStatus.BAD_REQUEST, message));
	});

	return errorMessages;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorMessage handle(IllegalArgumentException exception)
    {
	return new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
