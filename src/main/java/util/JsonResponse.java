package util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonResponse {
    public static <T> void send(HttpServletResponse response, T payload, int statusCode) throws IOException {
        Gson gson = new Gson();
        PrintWriter writer = response.getWriter();

        response.setStatus(statusCode);

        String json = gson.toJson(payload);
        writer.write(json);
        writer.close();
    }
}
