package com.syz.community.mapper;

import com.syz.community.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    void insUser(User user);

    User findByToken(String token);

    User findUserByAccountId(long accountId);

    void updateUser(User user);
}
