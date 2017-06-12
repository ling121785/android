package com.example.businessmodule.event.simpleEvent;

/**
 * 验证失败事件，直接 post 到 UI
 */
public class AuthFailEvent {

    public static final String TOKEN_IS_NULL="token is null";
    private String message;

    public AuthFailEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
