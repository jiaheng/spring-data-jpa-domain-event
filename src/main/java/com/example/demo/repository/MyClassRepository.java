package com.example.demo.repository;

import com.example.demo.entity.MyClass;
import com.example.demo.entity.Status;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MyClassRepository extends JpaRepository<MyClass, UUID> {
    Window<MyClass> findFirst5ByStatus(Status status, KeysetScrollPosition position);
}
