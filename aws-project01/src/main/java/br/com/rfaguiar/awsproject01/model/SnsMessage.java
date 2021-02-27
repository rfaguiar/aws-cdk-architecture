package br.com.rfaguiar.awsproject01.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnsMessage {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("TopicArn")
    private String topicArn;

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("MessageId")
    private String messageId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"message\":\"")
                .append(Objects.toString(message, "")).append('\"');
        sb.append(",\"type\":\"")
                .append(Objects.toString(type, "")).append('\"');
        sb.append(",\"topicArn\":\"")
                .append(Objects.toString(topicArn, "")).append('\"');
        sb.append(",\"timestamp\":\"")
                .append(Objects.toString(timestamp, "")).append('\"');
        sb.append(",\"messageId\":\"")
                .append(Objects.toString(messageId, "")).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
