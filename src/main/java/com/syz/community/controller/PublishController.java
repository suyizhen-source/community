package com.syz.community.controller;

import com.syz.community.mapper.QuestionMapper;
import com.syz.community.pojo.Question;
import com.syz.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    //質問するをクリックする場合
    @GetMapping("/publish")
    public String publish() {
        return "/publish";
    }
    //質問をコミットする場合
    @PostMapping("/publish")
    public String doPublish(String title,String description, String tag, HttpServletRequest request, Model model) {
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
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.addQuestion(question);
        return "redirect:/";
    }
}
