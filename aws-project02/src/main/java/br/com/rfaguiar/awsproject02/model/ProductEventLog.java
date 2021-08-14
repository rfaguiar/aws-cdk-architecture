package br.com.rfaguiar.awsproject02.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "product-events")
public class ProductEventLog {

    @Id
    private ProductEventKey productEventKey;

    @DynamoDBAttribute(attributeName = "ttl")
    private Long ttl;

    @DynamoDBAttribute(attributeName = "poductId")
    private Long poductId;

    @DynamoDBAttribute(attributeName = "username")
    private String username;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "eventType")
    private EventType eventType;

    @DynamoDBAttribute(attributeName = "timestamp")
    private Long timestamp;

    @DynamoDBAttribute(attributeName = "messageId")
    private String messageId;


    public ProductEventLog() {
    }

    @DynamoDBHashKey(attributeName = "pk")
    public String getPk() {
        return this.productEventKey != null ? this.productEventKey.getPk() : null;
    }

    public void setPk(String pk) {
        if (this.productEventKey == null) {
            this.productEventKey = new ProductEventKey();
        }
        this.productEventKey.setPk(pk);
    }


    @DynamoDBRangeKey(attributeName = "sk")
    public String getSk() {
        return this.productEventKey != null ? this.productEventKey.getSk() : null;
    }

    public void setSk(String sk) {
        if (this.productEventKey == null) {
            this.productEventKey = new ProductEventKey();
        }
        this.productEventKey.setSk(sk);
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Long getPoductId() {
        return poductId;
    }

    public void setPoductId(Long poductId) {
        this.poductId = poductId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
