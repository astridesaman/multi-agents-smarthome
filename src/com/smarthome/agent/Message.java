package com.smarthome.agent;

import java.util.*;

/**
 * Message class for inter-agent communication
 */
public class Message {
    public enum MessageType {
        REQUEST, RESPONSE, INFORM, QUERY, NEGOTIATE
    }

    private String senderId;
    private String receiverId;
    private MessageType type;
    private String content;
    private Object data;
    private long timestamp;

    public Message(String senderId, String receiverId, MessageType type, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public MessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Message[from=%s, to=%s, type=%s, content=%s]",
                senderId, receiverId, type, content);
    }
}
