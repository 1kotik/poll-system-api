package by.kotik.pollservice.exception;

import exception.GenericNotFoundException;

import java.util.UUID;

public class PollNotFoundException extends GenericNotFoundException {
    public PollNotFoundException(UUID pollId) {
        super(String.format("Poll with id %s not found", pollId.toString()));
    }
}
