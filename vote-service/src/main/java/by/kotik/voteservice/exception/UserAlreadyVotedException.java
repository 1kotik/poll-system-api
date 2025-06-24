package by.kotik.voteservice.exception;

import exception.GenericValidationException;

import java.util.UUID;

public class UserAlreadyVotedException extends VotingException {
    public UserAlreadyVotedException() {
        super("User already voted.");
    }
}
