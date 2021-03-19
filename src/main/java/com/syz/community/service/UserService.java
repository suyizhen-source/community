package com.syz.community.service;

import com.syz.community.mapper.UserMapper;
import com.syz.community.model.User;
import com.syz.community.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * userについての業務処理
 * */

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()==0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            user.setGmtModified(System.currentTimeMillis());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(user,example);
        }
    }
}
