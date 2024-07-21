package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.usermapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.userservice;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class userservicelmpl implements userservice {
    public static final String url = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private usermapper usermapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {

        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("grant_type", "authorization_code");
        map.put("js_code", userLoginDTO.getCode());
        String doneGet = HttpClientUtil.doGet(url, map);
        JSONObject object = JSON.parseObject(doneGet);
        String openid = object.getString("openid");
        if (openid == null) {
            throw new LoginFailedException("登录失败");
        }
        User user = usermapper.get(userLoginDTO.getCode());
        if (user!=null) {
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setOpenid(openid);
            userLoginVO.setId(user.getId());
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
            userLoginVO.setToken(jwt);
            return userLoginVO;
        } else {
            User newuser = new User();
            newuser.setOpenid(openid);
            newuser.setCreateTime(LocalDateTime.now());
            usermapper.insert(newuser);
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setOpenid(openid);
            userLoginVO.setId(newuser.getId());
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", newuser.getId());
            String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
            userLoginVO.setToken(jwt);
            return userLoginVO;
        }
    }
}