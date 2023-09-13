package com.example.demo.entity;

import com.example.demo.event.MyClassUpdateEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.Instant;
import java.util.UUID;

@DynamicUpdate
@DynamicInsert
@Entity
public class MyClass extends AbstractAggregateRoot<MyClass> {
    @Id
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private String field;

    @UpdateTimestamp
    private Instant updatedAt;
    @CreationTimestamp
    private Instant createdAt;
    @Version
    private Long version;

    public MyClass(UUID id, Status status, String field) {
        this.id = id;
        this.status = status;
        this.field = field;
    }

    protected MyClass() {
    }

    @JsonProperty
    public UUID id() {
        return id;
    }

    @JsonProperty
    public String field() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
        registerEvent(new MyClassUpdateEvent(this));
    }

    @JsonProperty
    public Status status() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("field", field)
                .append("updatedAt", updatedAt)
                .append("createdAt", createdAt)
                .append("version", version)
                .toString();
    }
}
