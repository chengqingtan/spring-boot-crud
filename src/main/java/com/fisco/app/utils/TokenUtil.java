package com.fisco.app.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    @Value("${token.secretKey}")
    private String secretKey;

    /**
     * 加密token.
     */
    public String getToken(String username, String role) {
        //这个是放到负载payLoad 里面,魔法值可以使用常量类进行封装.
        String token = JWT.create()
                .withClaim("username" ,username)
                .withClaim("role", role)
                .withClaim("timeStamp", System.currentTimeMillis())
                .sign(Algorithm.HMAC256(secretKey));
        return token;
    }

    /**
     * 解析token.
     * {
     * "username": "weizhong",
     * "role": "ROLE_ADMIN",
     * "timeStamp": "134143214"
     * }
     */
    public Map<String, String> parseToken(String token) {
        HashMap<String, String> map = new HashMap<String, String>();
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);
        Claim userId = decodedjwt.getClaim("username");
        Claim userRole = decodedjwt.getClaim("role");
        Claim timeStamp = decodedjwt.getClaim("timeStamp");
        map.put("username", userId.asString());
        map.put("role", userRole.asString());
        map.put("timeStamp", timeStamp.asLong().toString());
        return map;
    }

    public String getUsername(String token) {
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);
        Claim userId = decodedjwt.getClaim("username");
        return userId.asString();
    }

    public String getRole(String token) {
        DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);
        Claim userRole = decodedjwt.getClaim("role");
        return userRole.asString();
    }
}
