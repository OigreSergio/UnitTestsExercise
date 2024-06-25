package com.OIgres.UnitTestExercise.repos;

import com.OIgres.UnitTestExercise.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUser extends JpaRepository<UserEntity, Long> {
}