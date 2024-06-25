package com.OIgres.UnitTestExercise;

import com.OIgres.UnitTestExercise.repos.RepoUser;
import com.OIgres.UnitTestExercise.services.ServicesUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles(value = "test")
public class UserServiceTest {


    @Autowired
    private RepoUser repo;
    @Autowired
    private ServicesUser service;


    UserEntity userTest = new UserEntity(1L, "Sergio", "Hanganu");
    UserEntity userTest2 = new UserEntity(2L, "Sergio", "HAnganu");
    @Test
    public void testSearchSingleUser() {
        repo.save(userTest);
        UserEntity result = repo.findById(userTest.getId()).get();
        assertEquals(result.getId(), userTest.getId());
    }
    @Test
    public void testCreateUser() throws Exception {
       service.create(userTest);
       assertTrue(repo.existsById(userTest.getId()));
    }

    @Test
    public void testSearchAllUsers() {


        repo.save(userTest);
        repo.save(userTest2);
        List<UserEntity> result = service.searchAll();
        assertNotNull(result);
    }

    @Test
    public void testUpdateUser() {
        repo.save(userTest);
        UserEntity result = service.update(userTest.getId(), userTest2);
        assertEquals(result.getId(), userTest.getId());
        assertEquals(result.getName(), userTest2.getName());
    }

    @Test
    public void testDeleteUser(){
        repo.save(userTest);
        service.delete(userTest.getId());
        assertFalse(repo.existsById(userTest.getId()));
    }
}