package com.jeremy.messenger.views.messenger;

import java.time.LocalDateTime;

public class ChatMessage {
    private String from;
    private LocalDateTime time;
    private String message;
    
    ChatMessage(String from, String message) {
        this.from = from;
        this.message = message;
        this.time = LocalDateTime.now();
    }
    
    String getFrom() {
        return from;
    }
    
    String getMessage() {
        return message;
    }

    LocalDateTime getTime() {
        return time;
    }
}
