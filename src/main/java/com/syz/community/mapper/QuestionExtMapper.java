package com.syz.community.mapper;

import com.syz.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int addViewCount(Question question);
    int addCommentCount(Question question);
    List<Question> selectRelated(Question question);
    List<Question> selectBySearch(String search);

}