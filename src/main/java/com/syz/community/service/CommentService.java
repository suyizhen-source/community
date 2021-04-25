package com.syz.community.service;

import com.syz.community.dto.CommentDTO;
import com.syz.community.enums.CommentTypeEnum;
import com.syz.community.enums.NotificationStatusEnum;
import com.syz.community.enums.NotificationTypeEnum;
import com.syz.community.exception.CustomizeErrorCode;
import com.syz.community.exception.CustomizeException;
import com.syz.community.mapper.*;
import com.syz.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionExtMapper questionExtMapper;
    @Resource
    private CommentExtMapper commentExtMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //コメントを返事する
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //コメントする問題を取得する
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            dbComment.setCommentCount(1);
            commentExtMapper.addCommentCount(dbComment);
            //メッセージを追加
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question, NotificationTypeEnum.REPLY_COMMENT);
        } else {
            //問題を返事する
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            } else {
                commentMapper.insertSelective(comment);
                dbQuestion.setCommentCount(1);
                questionExtMapper.addCommentCount(dbQuestion);
                //メッセージを追加
                createNotify(comment, dbQuestion.getCreator(), commentator.getName(), dbQuestion, NotificationTypeEnum.REPLY_QUESTION);
            }
        }
    }

    public void createNotify(Comment comment, Integer receiver, String notifierName, Question question, NotificationTypeEnum notificationTypeEnum){
        if (receiver==comment.getCommentator()&& (notificationTypeEnum.getType() == 1 || notificationTypeEnum.getType() == 2)){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        notification.setOuterId(question.getId());
        notification.setNotifier(comment.getCommentator());
        notification.setNotifierName(notifierName);
        notification.setReceiver(receiver);
        notification.setOuterTitle(question.getTitle());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> getComListByQueId(Integer id, CommentTypeEnum typeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id).
                andTypeEqualTo(typeEnum.getType());
        commentExample.setOrderByClause("GMT_CREATE DESC");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //コメント者idを取得する
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> usersId = new ArrayList<>();
        usersId.addAll(commentators);

        //idでコメント者を取得する key:id values:user
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(usersId);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //comment →　commentDTOへ変更する
        List<CommentDTO> commentDTOList = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOList;
    }
}
