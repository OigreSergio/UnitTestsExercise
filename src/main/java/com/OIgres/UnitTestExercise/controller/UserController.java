package com.OIgres.UnitTestExercise.controller;

import com.OIgres.UnitTestExercise.UserEntity;
import com.OIgres.UnitTestExercise.services.ServicesUser;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private ServicesUser service;

    @PostMapping
    public UserEntity createUser (@RequestBody UserEntity user){
        return service.create(user);
    }

    @GetMapping
    public List<UserEntity> showUsers (){
        return service.searchAll();
    }

    @GetMapping("/{id}")
    public UserEntity showASingleUser (@PathVariable Long id){
        return service.searchOne(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return ResponseEntity.ok(service.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}