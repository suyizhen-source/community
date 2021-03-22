package com.syz.community.controller;

import com.syz.community.dto.PaginationDTO;
import com.syz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * ホームページ画面コントロール
 * */

@Controller
public class IndexController {

    @Resource
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value="pageNo",defaultValue="1")int pageNo,
                        @RequestParam(value="pageSize",defaultValue="5")int pageSize) {
        PaginationDTO questionList=questionService.getQuestionList(pageNo,pageSize);
        model.addAttribute("questionList",questionList);
        return "index";
    }
}
