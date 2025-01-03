package exception;

public class DatabaseException extends AppException {
    public DatabaseException() {
        super(ExceptionError.INTERNAL_ERROR_IN_DATABASE);
    }

}
