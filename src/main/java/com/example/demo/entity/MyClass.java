package com.example.demo.entity;

import com.example.demo.event.MyClassUpdateEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.UUID;

@DynamicUpdate
@DynamicInsert
@Entity
public class MyClass extends AbstractAggregateRoot<MyClass> {
    @Id
    private UUID id;

    private String field;

    @Version
    private Long version;

    protected MyClass() {
    }

    public UUID id() {
        return id;
    }

    public String field() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
        registerEvent(new MyClassUpdateEvent(this));
    }
}
