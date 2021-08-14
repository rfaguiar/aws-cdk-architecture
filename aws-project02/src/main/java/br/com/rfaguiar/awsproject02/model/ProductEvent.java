package br.com.rfaguiar.awsproject02.model;

import java.util.Objects;

public class ProductEvent {

    private Long productId;
    private String code;
    private String username;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"productId\":")
                .append(productId);
        sb.append(",\"code\":\"")
                .append(Objects.toString(code, "")).append('\"');
        sb.append(",\"username\":\"")
                .append(Objects.toString(username, "")).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
