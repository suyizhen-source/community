package com.syz.community.controller;

import com.syz.community.dto.NotificationDTO;
import com.syz.community.enums.NotificationTypeEnum;
import com.syz.community.model.User;
import com.syz.community.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Resource
    NotificationService notificationService;

    @RequestMapping("/notification/{id}")
    public String edit(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.readNotification(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() ||
                NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType() ||
                NotificationTypeEnum.THUMB_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        }
        return "/profile";
    }
}
