package by.kotik.pollservice.exception;

import exception.GenericNotFoundException;

public class OptionNotFoundException extends GenericNotFoundException {
    public OptionNotFoundException() {
        super("Option not found");
    }
}
