package by.kotik.voteservice.exception;

public class ClosedPollException extends VotingException {

    public ClosedPollException() {
        super("Poll is closed.");
    }
}
