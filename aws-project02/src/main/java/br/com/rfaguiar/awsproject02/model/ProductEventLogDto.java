package br.com.rfaguiar.awsproject02.model;

public class ProductEventLogDto {

    private String messageId;
    private String code;
    private Long productId;
    private String username;
    private EventType eventType;
    private Long timestamp;

    public ProductEventLogDto(ProductEventLog productEventLog) {
        this.code = productEventLog.getPk();
        this.eventType = productEventLog.getEventType();
        this.productId = productEventLog.getPoductId();
        this.username = productEventLog.getUsername();
        this.timestamp = productEventLog.getTimestamp();
        this.messageId = productEventLog.getMessageId();
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCode() {
        return code;
    }

    public Long getProductId() {
        return productId;
    }

    public String getUsername() {
        return username;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
