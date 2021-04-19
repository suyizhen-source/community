package com.syz.community.controller;

import com.syz.community.cache.HotTagCache;
import com.syz.community.dto.PaginationDTO;
import com.syz.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ホームページ画面コントロール
 * */

@Controller
public class IndexController {

    @Resource
    private QuestionService questionService;

    @Resource
    private HotTagCache hotTagCache;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value="pageNo",defaultValue="1")int pageNo,
                        @RequestParam(value="pageSize",defaultValue="10")int pageSize,
                        @RequestParam(value="search",required = false,defaultValue = "")String search,
                        @RequestParam(value="tag",required = false,defaultValue = "")String tag,
                        @RequestParam(value="sort",defaultValue = "new",required = false)String sort) {
        PaginationDTO questionList=questionService.getQuestionList(search,tag,sort,pageNo,pageSize);
        List<String> tags = hotTagCache.getTags();
        model.addAttribute("questionList",questionList);
        model.addAttribute("search",search);
        model.addAttribute("tags",tags);
        model.addAttribute("tag",tag);
        model.addAttribute("sort",sort);
        return "index";
    }
}
