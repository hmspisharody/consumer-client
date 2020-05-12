package com.hms.consumerclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMessageHandler {

    public void handler(String message) {
        log.info("Message Received : " + message);
    }
}
