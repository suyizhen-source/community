package com.syz.community.controller;

import com.syz.community.dto.PaginationDTO;
import com.syz.community.model.User;
import com.syz.community.service.NotificationService;
import com.syz.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * マイページ画面コントロール
 */


@Controller
public class ProfileController {
    @Resource
    private QuestionService questionService;
    @Resource
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                          @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if (action.equals("questions")) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "私の質問");
            PaginationDTO questionList = questionService.getMyQuestionList(user.getId(), pageNo, pageSize);
            model.addAttribute("pagination", questionList);
        } else if (action.equals("replies")) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回答");
            PaginationDTO notificationList = notificationService.getMyNotification(user.getId(), pageNo, pageSize);
            model.addAttribute("pagination", notificationList);
        }
        return "profile";
    }
}
