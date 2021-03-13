package com.syz.community.service;

import com.syz.community.dto.QuestionDto;
import com.syz.community.mapper.QuestionMapper;
import com.syz.community.mapper.UserMapper;
import com.syz.community.pojo.Question;
import com.syz.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public List<QuestionDto> getQuestionList() {
        List<Question> questions = questionMapper.selAllQuestion();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question:questions){
            User user =userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
