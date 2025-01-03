package exception;


public class AppException extends RuntimeException {
    private final ExceptionError exceptionError;

    public AppException(ExceptionError exceptionError) {
        this.exceptionError = exceptionError;
    }


    public ExceptionError exceptionError() {
        return exceptionError;
    }

}
