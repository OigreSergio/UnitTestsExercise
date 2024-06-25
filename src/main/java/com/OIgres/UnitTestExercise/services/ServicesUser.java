package com.OIgres.UnitTestExercise.services;

import com.OIgres.UnitTestExercise.UserEntity;
import com.OIgres.UnitTestExercise.repos.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesUser {
    @Autowired
    private RepoUser repo;

    public UserEntity create (UserEntity user){
        return repo.save((user));
    }

    public List<UserEntity> searchAll (){
        return repo.findAll();
    }

    public UserEntity searchOne(long id){
        return repo.findById(id).get();
    }

    public UserEntity update(long id, UserEntity user) {
        UserEntity result = repo.findById(id).get();
        result.setName(user.getName());
        result.setSurname(user.getSurname());
        return result;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
