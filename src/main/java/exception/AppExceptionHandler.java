package exception;

import dto.ExceptionDto;
import jakarta.servlet.http.HttpServletResponse;
import util.JsonResponse;

import java.io.IOException;

public class AppExceptionHandler {
    public static void handle(HttpServletResponse resp, Exception ex) throws IOException {

        if (ex instanceof AppException) {
            AppException appException = (AppException) ex;

            String exceptionMessage = appException.exceptionError().message();
            int exceptionCode = appException.exceptionError().status();

            JsonResponse.send(resp, new ExceptionDto(exceptionMessage, exceptionCode), exceptionCode);
            return;
        }

        JsonResponse.send(resp, new ExceptionDto(ex.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    }
}
