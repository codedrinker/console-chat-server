package com.github.chat.controller.entity;

import lombok.Data;

@Data
public class Message {
    private String chatId;
    private String sendId;
    private String nickName;
    private String content;
    private Long time;
}
