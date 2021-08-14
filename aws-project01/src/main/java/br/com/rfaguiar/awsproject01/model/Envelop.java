package br.com.rfaguiar.awsproject01.model;

public class Envelop {

    private EventType eventType;
    private String data;

    public Envelop(EventType eventType) {
        this.eventType = eventType;
    }

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
}
