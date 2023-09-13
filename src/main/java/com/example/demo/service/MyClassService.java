package com.example.demo.service;

import com.example.demo.entity.MyClass;
import com.example.demo.entity.Status;
import com.example.demo.repository.MyClassRepository;
import com.github.f4b6a3.uuid.alt.GUID;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyClassService {
    private final MyClassRepository myClassRepository;

    public MyClassService(MyClassRepository myClassRepository) {
        this.myClassRepository = myClassRepository;
    }

    @Transactional
    public void createRandomMyClass() {
        MyClass myClass = new MyClass(GUID.v7().toUUID(), Status.CREATED, "new");
        myClassRepository.save(myClass);
    }

    @Transactional
    public void updateAllCreated() {
        Window<MyClass> myClasses =
                myClassRepository.findFirst5ByStatus(Status.CREATED, ScrollPosition.keyset());
            for (MyClass myClass : myClasses) {
                myClass.setField("updated");
                myClass.setStatus(Status.UPDATED);
            }
            myClassRepository.saveAll(myClasses);
    }

    public List<MyClass> getAll() {
        return myClassRepository.findAll();
    }
}
