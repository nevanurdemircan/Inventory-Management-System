package exception;

import jakarta.servlet.http.HttpServletResponse;

public enum ExceptionError {

    INTERNAL_ERROR("Internal application error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    INTERNAL_ERROR_IN_DATABASE("Internal database error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),

    CURRENCY_ALREADY_EXISTS("Currency with this code already exists", HttpServletResponse.SC_CONFLICT),
    CURRENCY_NOT_FOUND("Currency not found", HttpServletResponse.SC_NOT_FOUND),
    BAD_CURRENCIES_FIELDS("Bad currencies fields", HttpServletResponse.SC_BAD_REQUEST),
    CURRENCY_PAIR_IS_MISSING_IN_DATABASE("Currency pair is missing in the database", HttpServletResponse.SC_NOT_FOUND),
    CURRENCY_CODE_IS_NOT_PRESENT("Currency code is not exist in URL", HttpServletResponse.SC_BAD_REQUEST),
    BAD_CURRENCY_CODE("Bad currency code", HttpServletResponse.SC_BAD_REQUEST),

    EXCHANGE_RATE_ALREADY_EXISTS("Exchange rate already exists", HttpServletResponse.SC_CONFLICT),
    EXCHANGE_RATE_NOT_FOUND("Exchange rate with this currency codes is not found", HttpServletResponse.SC_NOT_FOUND),
    BAD_EXCHANGE_RATE_FIELDS("Bad exchange rate fields", HttpServletResponse.SC_BAD_REQUEST),
    BAD_EXCHANGE_FIELDS("Bad exchange fields", HttpServletResponse.SC_BAD_REQUEST),
    BAD_EXCHANGE_AMOUNT("Bad exchange amount", HttpServletResponse.SC_BAD_REQUEST);

    private final String message;
    private final int status;

    ExceptionError(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String message() {
        return message;
    }

    public int status() {
        return status;
    }
}
