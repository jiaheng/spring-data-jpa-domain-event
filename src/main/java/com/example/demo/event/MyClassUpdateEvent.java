package com.example.demo.event;

import com.example.demo.entity.MyClass;

public class MyClassUpdateEvent {

    private MyClass entity;

    public MyClassUpdateEvent(MyClass myClass) {
        this.entity = myClass;
    }

    public MyClass entity() {
        return entity;
    }
}
