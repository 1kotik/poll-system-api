package by.kotik.voteservice.exception;

import exception.AppException;

public class VotingException extends AppException {
    public VotingException(String message) {
        super(400, "VOTE_ERROR", message);
    }
}
