package com.syz.community.controller;

import com.syz.community.dto.QuestionDTO;
import com.syz.community.model.Question;
import com.syz.community.model.User;
import com.syz.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 質問追加機能コントロール
 * */


@Controller
public class PublishController {

    @Resource
    private QuestionService questionService;

    //質問を編集する場合
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        QuestionDTO question = questionService.getQuestionById(id);
        model.addAttribute("id",id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        return "/publish";
    }

    //質問するをクリックする場合
    @GetMapping("/publish")
    public String publish() {
        return "/publish";
    }
    //質問をコミットする場合
    @PostMapping("/publish")
    public String doPublish(Integer id,String title,String description, String tag, HttpServletRequest request, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title.equals("")) {
            model.addAttribute("error", "タイトルを入力してください。");
            return "/publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "質問内容を入力してください。");
            return "/publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "タグを入力してください。");
            return "/publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "ログインしてください。");
            return "/publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
