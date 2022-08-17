package com.example.liquibasedemo.repository;

import com.example.liquibasedemo.schema.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
//    @Query("SELECT p.name FROM Person p WHERE p.name LIKE %:personName")
//    String findByName(String personName);
}
