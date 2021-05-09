package com.syz.community.controller;

import com.syz.community.dto.PaginationDTO;
import com.syz.community.mapper.QuestionMapper;
import com.syz.community.model.User;
import com.syz.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 問題削除コントロール
 */
@Controller
public class DeleteQuestionController {
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionMapper questionMapper;

    @GetMapping("/profile/questions/delete/{id}")
    public String delete(@PathVariable(name = "id") Integer id,
                         Model model,
                         HttpServletRequest request,
                         @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                         @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("section", "questions");
        model.addAttribute("sectionName", "私の質問");
        questionMapper.deleteByPrimaryKey(id);
        PaginationDTO questionList = questionService.getMyQuestionList(user.getId(), pageNo, pageSize);
        model.addAttribute("pagination", questionList);
        return "profile";
    }
}
