package com.example.demo.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@EnableJpaRepositories(considerNestedRepositories = true)
@SpringBootTest
class DomainEventHandlerTest {

    @Autowired
    MyClassRepository myClassRepository;

    @Autowired
    MyClassService myClassService;

    @SpyBean
    EventHandler eventHandler;

    @Test
    void test() {
        myClassRepository.save(new MyClass());
        myClassService.update();
        then(eventHandler).should().handleMyClassUpdateEvent(any());
    }

    @TestConfiguration
    static class Config {
        @Bean
        MyClassService myClassService(MyClassRepository myClassRepository) {
            return new MyClassService(myClassRepository);
        }

        @Bean
        EventHandler eventHandler() {
            return new EventHandler();
        }
    }

    @Entity
    static class MyClass extends AbstractAggregateRoot<MyClass> {
        @Id
        @GeneratedValue
        private Integer id;

        private String field;

        public void setField(String field) {
            this.field = field;
            registerEvent(new MyClassUpdateEvent());
        }
    }

    @Repository
    interface MyClassRepository extends JpaRepository<MyClass, Integer> {
        Window<MyClass> findFirst1By(KeysetScrollPosition position);
    }

    static class MyClassUpdateEvent {
    }

    static class MyClassService {
        private final MyClassRepository myClassRepository;

        public MyClassService(MyClassRepository myClassRepository) {
            this.myClassRepository = myClassRepository;
        }

        public void update() {
            Window<MyClass> myClasses = myClassRepository.findFirst1By(ScrollPosition.keyset());
            for (MyClass myClass : myClasses) {
                myClass.setField("updated");
            }
            myClassRepository.saveAll(myClasses); // change myClasses to myClasses.toList() will pass
        }
    }

    static class EventHandler {
        @EventListener
        public void handleMyClassUpdateEvent(MyClassUpdateEvent event) {
        }
    }

}
