package com.github.chat.controller;

import com.github.chat.controller.entity.Message;
import com.github.chat.controller.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;


    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    @ResponseBody
    public void send(@RequestBody Message message) {
        messageRepository.send(message);
    }

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> read(@RequestParam(name = "chatId") String chatId,
                              @RequestParam(name = "readerId") String readerId,
                              @RequestParam(name = "watermark") Long watermark) {
        log.info("chat?chatId={}&readerId={}&watermark={}", chatId, readerId, watermark);
        return messageRepository.read(chatId, readerId, watermark);
    }
}
