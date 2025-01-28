package by.kapinskiy.p2w.controllers;

import by.kapinskiy.p2w.util.ErrorResponse;
import by.kapinskiy.p2w.util.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAlreadyExistsException.class, NotChangedException.class})
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityNotCreatedException.class, OfferAlreadyExistsException.class,
            InvalidTokenException.class, UserAlreadyVerifiedException.class,
            UserNotVerifiedException.class, MoneyLackException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotCreatedException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



}