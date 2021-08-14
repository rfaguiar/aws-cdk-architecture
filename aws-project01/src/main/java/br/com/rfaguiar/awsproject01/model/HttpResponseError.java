package br.com.rfaguiar.awsproject01.model;

public class HttpResponseError {
    private final Integer status;
    private final String message;

    public HttpResponseError(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
