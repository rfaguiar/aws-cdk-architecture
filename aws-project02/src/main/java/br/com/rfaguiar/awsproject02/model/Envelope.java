package br.com.rfaguiar.awsproject02.model;

import java.util.Objects;

public class Envelope {

    private EventType eventType;
    private String data;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"eventType\":")
                .append(eventType);
        sb.append(",\"data\":\"")
                .append(Objects.toString(data, "")).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
