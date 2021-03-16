package com.syz.community.service;

import com.syz.community.mapper.UserMapper;
import com.syz.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findUserByAccountId(user.getAccountId());
        if (dbUser==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insUser(user);
        }else {
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setToken(user.getToken());
            userMapper.updateUser(dbUser);
        }
    }
}
