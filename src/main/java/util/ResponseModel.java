package util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseModel {
    private boolean success;
    private String message;
    private Object data;

    public ResponseModel() {
    }

    public ResponseModel(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean success() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object data() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
