package by.kotik.pollservice.exception;

import exception.GenericNotFoundException;

public class PollNotFoundException extends GenericNotFoundException {
    public PollNotFoundException(String pollId) {
        super(String.format("Poll with id %s not found", pollId));
    }
}
