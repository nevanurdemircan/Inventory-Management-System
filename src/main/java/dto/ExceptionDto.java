package dto;

public class ExceptionDto {
    private String message;
    private int statusCode;

    public ExceptionDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ExceptionDto() {
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int statusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
