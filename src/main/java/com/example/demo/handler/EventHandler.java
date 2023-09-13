package com.example.demo.handler;

import com.example.demo.event.MyClassUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);

    @EventListener
    public void handleMyClassUpdateEvent(MyClassUpdateEvent event) {
        logger.atInfo()
                .setMessage("Handle event {}")
                .addArgument(() -> event.entity().toString())
                .log();
    }
}
