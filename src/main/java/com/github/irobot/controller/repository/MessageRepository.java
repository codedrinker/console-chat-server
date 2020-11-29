package com.github.irobot.controller.repository;

import com.github.irobot.controller.entity.Message;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MessageRepository {
    private static Cache<String, List<Message>> messages = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build();

    /**
     * 保存消息
     *
     * @param message
     */
    public void send(Message message) {
        try {
            message.setTime(System.currentTimeMillis());
            List<Message> messages = MessageRepository.messages.get(message.getChatId(), ArrayList::new);
            messages.add(0, message);
        } catch (Exception e) {
            log.error("send error", e);
        }
    }


    /**
     * 通过水位阅读消息
     *
     * @param chatId
     * @param readerId
     * @param watermark
     * @return
     */
    public List<Message> read(String chatId, String readerId, Long watermark) {
        try {
            List<Message> messages = MessageRepository.messages.get(chatId, ArrayList::new);
            List<Message> toReads = new ArrayList<>();
            for (Message message : messages) {
                if (readerId.equals(message.getSendId())) {
                    continue;
                }
                if (message.getTime() < watermark) {
                    break;
                }
                toReads.add(message);
            }
            return toReads;
        } catch (Exception e) {
            log.error("send error", e);
        }
        return new ArrayList<>();
    }
}
