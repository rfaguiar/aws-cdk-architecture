package br.com.rfaguiar.awsproject01.model;

public class ProductEvent {

    private Long productId;
    private String code;
    private String username;

    public ProductEvent(Long productId, String code, String username) {
        this.productId = productId;
        this.code = code;
        this.username = username;
    }

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
}
