package com.syz.community.mapper;

import com.syz.community.pojo.Question;
import com.syz.community.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionMapper {
    void addQuestion(Question question);
}
