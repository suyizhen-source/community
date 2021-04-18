package com.syz.community.controller;

import com.syz.community.dto.CommentDTO;
import com.syz.community.dto.QuestionDTO;
import com.syz.community.enums.CommentTypeEnum;
import com.syz.community.service.CommentService;
import com.syz.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * 個別質問照会機能コントロール
 * */

@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id , Model model){
        QuestionDTO questionDto = questionService.getQuestionById(id);
        List<QuestionDTO> relatedQuestions= questionService.selectRelated(questionDto);
        List<CommentDTO> commentList= commentService.getComListByQueId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("question",questionDto);
        model.addAttribute("commentList",commentList);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }

}
