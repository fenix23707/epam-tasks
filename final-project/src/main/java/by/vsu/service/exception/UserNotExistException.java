package by.vsu.service.exception;

public class UserNotExistException extends ServiceException{
    public UserNotExistException() {
        super();
    }

    public UserNotExistException(Throwable cause) {
        super(cause);
    }
}
