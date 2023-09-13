package com.example.demo.service;

import com.example.demo.entity.MyClass;
import com.example.demo.repository.MyClassRepository;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyClassService {
    private final MyClassRepository myClassRepository;

    public MyClassService(MyClassRepository myClassRepository) {
        this.myClassRepository = myClassRepository;
    }

    @Transactional
    public void updateFirst5() {
        Window<MyClass> myClasses = myClassRepository.findFirst5By(ScrollPosition.keyset());
        for (MyClass myClass : myClasses) {
            myClass.setField("updated");
        }
        myClassRepository.saveAll(myClasses);
    }

    @Transactional
    public void updateFirst5ToList() {
        Window<MyClass> myClasses = myClassRepository.findFirst5By(ScrollPosition.keyset());
        for (MyClass myClass : myClasses) {
            myClass.setField("updated");
        }
        myClassRepository.saveAll(myClasses.toList());
    }
}
