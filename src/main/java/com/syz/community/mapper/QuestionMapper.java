package com.syz.community.mapper;

import com.syz.community.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    void addQuestion(Question question);

    List<Question> selAllQuestion();

    List<Question> selQuestionByUser(long accountId);

    Question selById(int id);
}
