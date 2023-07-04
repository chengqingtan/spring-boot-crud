package com.fisco.app.controller;

import com.fisco.app.client.UserClient;
import com.fisco.app.entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserClient userClient;

    @PostMapping("test_post")
    public ResponseData test_post() {
        return ResponseData.success("success");
    }

    @PostMapping("/user_login")
    public ResponseData user_login(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用UserClient内的函数
        if(userClient.user_login(username, password))
            return ResponseData.success("success");
        else
            return ResponseData.error("failure");
    }

    @PostMapping("/admin_login")
    public ResponseData admin_login(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用UserClient内的函数
        if(userClient.admin_login(username, password))
            return ResponseData.success("success");
        else
            return ResponseData.error("failure");
    }

    @PostMapping("/register")
    public ResponseData register(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用UserClient内的函数
        if(userClient.register(username, password))
            return ResponseData.success("success");
        else
            return ResponseData.error("failure");
    }

    /**
     * 查询指定用户的余额
     * @param username 要查询用户的用户名
     * @return 如果有这个用户返回 ResponseData.success(balance) , 否则返回 ResponseData.error("can't find this user!")
     */
    @PostMapping("/query_balance")
    public ResponseData query_balance(@RequestParam("username") String username) {
        //调用UserClient内的函数

        int balance = userClient.query_balance(username);
        if(balance>=0)
            return ResponseData.success(balance);
        else
            return ResponseData.error("can't find this user!");
    }


    /**
     * 为指定用户添加余额
     * @param username 指定用户的用户名
     * @param amount 要添加的金额数
     * @return 如果有这个用户返回 ResponseData.success(“success") , 否则返回 ResponseData.error("can't find this user!")
     */
    @PostMapping("/add_balance")
    public ResponseData add_balance(@RequestParam("username") String username, @RequestParam("amount") int amount) {
        //调用UserClient内的函数
        boolean whether_add_success = userClient.add_balance(username, amount);
        if(whether_add_success)
            return ResponseData.success("success");
        else
            return ResponseData.error("can't find this user!");
    }

}
