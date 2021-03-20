package com.syz.community.mapper;

import com.syz.community.model.Question;

public interface QuestionExtMapper {
    int addViewCount(Question question);
}