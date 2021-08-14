package br.com.rfaguiar.awsproject01.model;

public class UrlResponse {

    private String url;
    private Long expirationTime;

    public UrlResponse(Long expirationTime, String url) {
        this.expirationTime = expirationTime;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

}
