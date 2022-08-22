package com.example.springwebservice.model;


import com.example.springwebservice.model.Entity.User;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    Long deleteById(int id);

    List<User> findByAgeGreaterThanEqual(Integer age);

    List<User> findByOrderByAgeDesc();

    @Query(value = "SELECT * FROM member WHERE name=?1 and age=?2",nativeQuery = true)
    List<User> findByNameAndAge(String name, int age);

    @Query(value = "INSERT INTO member VALUE (?1, ?2, ?3)",nativeQuery = true)
    void createUserBySql(int id, String name, int age);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member SET name=?2 , age=?3 WHERE id=?1",nativeQuery = true)
    int updateUserBySql(int id, String name, int age);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM member WHERE name=?1 and age=?2",nativeQuery = true)
    int deleteByNameAndAge(String name, int age);

}
