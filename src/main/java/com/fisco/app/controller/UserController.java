package com.fisco.app.controller;

import com.fisco.app.client.UserClient;
import com.fisco.app.entity.ResponseData;
import com.fisco.app.entity.User;
import com.fisco.app.enums.CookieName;
import com.fisco.app.utils.CookieUtil;
import com.fisco.app.utils.TokenUtil;
import io.netty.handler.codec.http.cookie.CookieHeaderNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    private UserClient userClient;

    @PostMapping("/testToken")
    public String testToken(HttpServletRequest request){
        String token = CookieUtil.getToken(request);
        tokenUtil.parseToken(token);
        return "请求成功";
    }

    @PostMapping("/login")
    public ResponseData login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        // 先验证用户的账号密码,账号密码验证通过之后，生成Token
        if(userClient.login(username, password)) {
            //验证身份成功
            //查询身份
            String role = userClient.query_role(username);
            String token = tokenUtil.getToken(username, role);
            response.addCookie(new Cookie(CookieName.ACCESS_TOKEN, token));
            response.addCookie(new Cookie(CookieName.USERNAME, username));
            return ResponseData.success("登录成功");
        }
        else
            return ResponseData.error("登录失败，请检查用户名或密码");

    }

    @PostMapping("/register")
    public ResponseData register(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用UserClient内的函数
        if(userClient.register(username, password))
            return ResponseData.success("success");
        else
            return ResponseData.error("用户名已存在");
    }

    /**
     * 查询自己的余额
     * @return 如果有这个用户返回 ResponseData.success(balance) , 否则返回 ResponseData.error("can't find this user!")
     */
    @RequestMapping("/query_balance")
    public ResponseData query_balance(HttpServletRequest request) {
        //提取token
        String token = CookieUtil.getToken(request);
        String username = tokenUtil.getUsername(token);
        String role = tokenUtil.getRole(token);
        //验证身份
        if(User.ROLE_USER.equals(role)) {
            int balance = userClient.query_balance(username);
            if(balance>=0)
                return ResponseData.success(balance);
            else
                return ResponseData.error("can't find this user!");
        }
        else
            return ResponseData.error("必须是普通用户才能添加余额!");
    }


    /**
     * 为自己添加余额
     * @param amount 要添加的金额数
     * @return 如果有这个用户返回 ResponseData.success(“success") , 否则返回 ResponseData.error("can't find this user!")
     */
    @PostMapping("/add_balance")
    public ResponseData add_balance(@RequestParam("amount") int amount, HttpServletRequest request) {
        //提取token
        String token = CookieUtil.getToken(request);
        String username = tokenUtil.getUsername(token);
        String role = tokenUtil.getRole(token);
        //验证身份
        if(User.ROLE_USER.equals(role)) {
            boolean whether_add_success = userClient.add_balance(username, amount);
            if(whether_add_success)
                return ResponseData.success("添加余额成功");
            else
                return ResponseData.error("can't find this user!");
        }
        else
            return ResponseData.error("必须是普通用户才能添加余额!");
    }

}
