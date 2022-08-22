package com.example.springwebservice.service;

import com.example.springwebservice.controller.dto.request.CreateUserRequest;
import com.example.springwebservice.controller.dto.request.UpdateUserRequest;
import com.example.springwebservice.controller.dto.response.StatusResponse;
import com.example.springwebservice.model.Entity.User;
import com.example.springwebservice.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    List<User> userList;


    public List<User> getUserList(Integer age , int ageFilter) {

        List<User> response = new ArrayList<>();

        switch (ageFilter){
            //全部的 User
            case 1:
                response = userRepository.findAll();
                break;
            // Age 大於等於輸入的 age
            case 2:
                response = userRepository.findByAgeGreaterThanEqual(age);
                break;
            //倒序
            case 3:
                response = userRepository.findByOrderByAgeDesc();
                break;
        }

        return response;
    }

    public User getUserById(int id) {

        User response = userRepository.findById(id);

        return response;
    }

    public String createUser(CreateUserRequest request) {

        //新增一個空的 user 的 Entity
        User user = new User();

        int createId = request.getId();
        if(null != userRepository.findById(createId)){
            return "Had This User";
        }

        //放入資料: user 從 request 拿資料
        user.setId(request.getId());
        user.setName(request.getName());
        user.setAge(request.getAge());

        //存入DB
        userRepository.save(user);

        //告訴 Controller 已存入 DB
        return "OK";
    }

    public String updateUser(int id, UpdateUserRequest request) {

        //確認有沒有這筆資料
        User user = userRepository.findById(id);

        if(null == user){
            return "Not Found User";
        }
        user.setName(request.getName());
        user.setAge(request.getAge());

        //存入DB
        userRepository.save(user);

        //告訴 Controller 已存入 DB
        return "OK";
    }

    public String deleteUserById(int id) {

        User user = userRepository.findById(id);

        if(null == user){
            return "Not Found User";
        }

        Long count = userRepository.deleteById(id);

        return "OK";
    }

    //用 SQL
    public List<User> getUserByNameAndAge(String name, int age) {

        List<User> user = userRepository.findByNameAndAge(name, age);

        return user;
    }

    public String createUserBySql(CreateUserRequest request) {

        userRepository.createUserBySql(request.getId(),request.getName(),request.getAge());

        return "OK";
    }

    public String updateUserBySql(int id, UpdateUserRequest user) {

        int count = userRepository.updateUserBySql(id,user.getName(),user.getAge());
        String response = "FAIL";

        if(0 < count){
            response = "OK";
        }
        return response;
    }

    public String deleteByNameAndAge(String name, int age) {

        List<User> user = userRepository.findByNameAndAge(name, age);

        if(null == user){
            return "Not Found User";
        }

        int count = userRepository.deleteByNameAndAge(name,age);

        return "OK";
    }


} // Class end
