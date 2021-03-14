package com.syz.community.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syz.community.dto.PaginationDTO;
import com.syz.community.dto.QuestionDto;
import com.syz.community.mapper.UserMapper;
import com.syz.community.pojo.User;
import com.syz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value="pageNo",defaultValue="1")int pageNo,
                        @RequestParam(value="pageSize",defaultValue="5")int pageSize) {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        PaginationDTO questionList=questionService.getQuestionList(pageNo,pageSize);
        model.addAttribute("questionList",questionList);
        return "index";
    }
}
