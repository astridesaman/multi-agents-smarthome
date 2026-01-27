package com.smarthome.agent;

import java.util.*;

/**
 * Message broker for agent-to-agent communication
 * Implements centralized communication coordination
 */
public class MessageBroker {
    private static MessageBroker instance;
    private Map<String, Queue<Message>> mailboxes;
    private List<Message> allMessages;

    private MessageBroker() {
        this.mailboxes = new HashMap<>();
        this.allMessages = new ArrayList<>();
    }

    public static synchronized MessageBroker getInstance() {
        if (instance == null) {
            instance = new MessageBroker();
        }
        return instance;
    }

    public void registerAgent(String agentId) {
        if (!mailboxes.containsKey(agentId)) {
            mailboxes.put(agentId, new LinkedList<>());
        }
    }

    public void sendMessage(Message message) {
        allMessages.add(message);
        String receiverId = message.getReceiverId();
        if (mailboxes.containsKey(receiverId)) {
            mailboxes.get(receiverId).add(message);
            System.out.println("[MSG] " + message.getSenderId() + " -> " + receiverId + ": " + message.getContent());
        }
    }

    public Message getNextMessage(String agentId) {
        Queue<Message> mailbox = mailboxes.get(agentId);
        if (mailbox != null && !mailbox.isEmpty()) {
            return mailbox.poll();
        }
        return null;
    }

    public List<Message> getAllMessages(String agentId) {
        List<Message> messages = new ArrayList<>();
        Queue<Message> mailbox = mailboxes.get(agentId);
        if (mailbox != null) {
            messages.addAll(mailbox);
            mailbox.clear();
        }
        return messages;
    }

    public int getMessageCount(String agentId) {
        Queue<Message> mailbox = mailboxes.get(agentId);
        return mailbox != null ? mailbox.size() : 0;
    }

    public void reset() {
        mailboxes.clear();
        allMessages.clear();
    }
}
