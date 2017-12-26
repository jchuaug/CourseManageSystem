package xmu.crms.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {};
    public UserAlreadyExistException(String msg) {
        super(msg);
    };

}
