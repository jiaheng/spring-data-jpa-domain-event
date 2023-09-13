package com.example.demo.service;

import com.example.demo.handler.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MyClassServiceTest {

    @Autowired
    MyClassService myClassService;

    @MockBean
    EventHandler eventHandler;

    @Test
    @DirtiesContext
    @Sql(scripts = {"/import_my_class.sql"})
    public void testDomainEventHandler() {
        myClassService.updateFirst5();
        verify(eventHandler, times(5)).handleMyClassUpdateEvent(notNull());
    }

    @Test
    @DirtiesContext
    @Sql(scripts = {"/import_my_class.sql"})
    public void testDomainEventHandlerToList() {
        myClassService.updateFirst5ToList();
        verify(eventHandler, times(5)).handleMyClassUpdateEvent(any());
    }
}
