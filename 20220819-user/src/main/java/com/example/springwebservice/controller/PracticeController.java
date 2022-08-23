package com.example.springwebservice.controller;


import com.example.springwebservice.controller.dto.request.CreateUserRequest;
import com.example.springwebservice.controller.dto.request.UpdateUserRequest;
import com.example.springwebservice.controller.dto.response.StatusResponse;
import com.example.springwebservice.model.Entity.User;
import com.example.springwebservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/practice/user")
public class PracticeController {

    @Autowired
    UserService userService;

    /**
     * 用 name 和 age 取得 User
     */
    @GetMapping
    public List<User> getUserLists(@RequestParam(required = false)String name, @RequestParam(required = false)int age) {

        List<User> response = userService.getUserByNameAndAge(name, age);

        return response;
    }

    /**
     * 實作 Part4-1
     * 取得一個 list 只有 name，且不重複並排序的資料
     */
    @GetMapping("/practice4-1")
    public List<String> getUserListsBuild() {

        List<User> response = userService.getUserList(null,1);

        List<String> userList = response.stream().map(User::getName).distinct().collect(Collectors.toList());

        return userList;
    }

    /**
     * 實作 Part4-2
     * 取得一個 map，其 key 為 ID；value 為 name
     */
    @GetMapping("/practice4-2")
    public Map<Integer, String> getUserListsBuild2() {

        List<User> response = userService.getUserList(null,1);

        Map<Integer, String> mapList = response.stream().collect(Collectors.toMap(u -> u.getId(), u -> u.getName()));

        return mapList;
    }

    /**
     * 實作 Part4-3
     * 取得第一筆 name = KZ 的資料
     */
    @GetMapping("/practice4-3")
    public User getUserListsBuild3() {

        List<User> response = userService.getUserList(null,1);

        User get = response.stream().filter(u -> "KZ".equals(u.getName())).findFirst().get();

        return get;
    }

    /**
     * 實作 Part4-4
     * 將資料先依據 age 排序，再依據 ID 排序
     */
    @GetMapping("/practice4-4")
    public List<User> getUserListsBuild4() {

        List<User> response = userService.getUserList(null,1);

        List<User> get = response.stream().sorted(Comparator.comparing(User::getId))
                .sorted(Comparator.comparing(User::getAge))
                .collect(Collectors.toList());

        return get;
    }

    /**
     * 實作 Part4-5
     * 取得一個 string 為所有資料的 name, age|name, age
     */
    @GetMapping("/practice4-5")
    public String getUserListsBuild5() {

        List<User> response = userService.getUserList(null,1);

        String userList = response.stream()
                            .map(u -> String.valueOf(u.getName()) + "," + String.valueOf(u.getAge()))
                            .collect(Collectors.joining("|"));

        return userList;
    }
    @PostMapping()
    public StatusResponse createUserBySql(@RequestBody CreateUserRequest request) {

        String response = userService.createUserBySql(request);

        return new StatusResponse(response);
    }

    @PutMapping("/{id}")
    public StatusResponse updateUserBySql(@PathVariable int id,@RequestBody UpdateUserRequest request) {

        String response = userService.updateUserBySql(id,request);

        return new StatusResponse(response);
    }

    @DeleteMapping()
    public StatusResponse deleteUserById(@RequestParam(required = false)String name, @RequestParam(required = false)int age) {

        String response = userService.deleteByNameAndAge(name, age);

        return new StatusResponse(response);
    }




}
