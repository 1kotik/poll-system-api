package by.kotik.pollservice.exception;

import exception.GenericValidationException;

public class InvalidPollDuration extends GenericValidationException {

    public InvalidPollDuration(String message) {
        super(message);
    }
}
