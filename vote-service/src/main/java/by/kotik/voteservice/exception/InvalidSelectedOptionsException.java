package by.kotik.voteservice.exception;

public class InvalidSelectedOptionsException extends VotingException {
    public InvalidSelectedOptionsException() {
        super("Invalid options selected");
    }
}
