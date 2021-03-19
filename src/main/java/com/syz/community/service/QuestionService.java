package com.syz.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syz.community.dto.PaginationDTO;
import com.syz.community.dto.QuestionDto;
import com.syz.community.exception.CustomizeErrorCode;
import com.syz.community.exception.CustomizeException;
import com.syz.community.exception.ICustomizeErrorCode;
import com.syz.community.mapper.QuestionMapper;
import com.syz.community.mapper.UserMapper;
import com.syz.community.model.Question;
import com.syz.community.model.QuestionExample;
import com.syz.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * questionについての業務処理
 * */

@Service
public class QuestionService {

    @Resource
    QuestionMapper questionMapper;

    @Resource
    UserMapper userMapper;

    public PaginationDTO getQuestionList(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Question> questions = questionMapper.selectByExample(new QuestionExample());
        PageInfo<Question>pageInfo = new PageInfo<>(questions);
        PaginationDTO paginationDTO = getPaginationDTO(pageInfo,pageNo);
        return paginationDTO;
    }

    public PaginationDTO getMyQuestionList(Integer accountId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(accountId);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        PageInfo<Question>pageInfo = new PageInfo<>(questions);
        PaginationDTO paginationDTO = getPaginationDTO(pageInfo,pageNo);
        return paginationDTO;
    }

    public QuestionDto getQuestionById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = getQuestionDto(question);
        return questionDto;
    }
    public QuestionDto getQuestionDto(Question question){
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;
    };
    public PaginationDTO  getPaginationDTO(PageInfo<Question> pageInfo,int pageNo){
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question:pageInfo.getList()){
            User user =userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        int totalPage = (int) pageInfo.getPages();
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageNo > totalPage) {
            pageNo = totalPage;
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(questionDtoList);
        paginationDTO.setShowPrevious(pageInfo.isHasPreviousPage());
        paginationDTO.setShowFirstPage(!(pageInfo.isIsFirstPage()));
        paginationDTO.setShowNext(pageInfo.isHasNextPage());
        paginationDTO.setShowEndPage(!(pageInfo.isIsLastPage()));
        paginationDTO.setPagination(totalPage, pageNo);
        return paginationDTO;
    };

    public void createOrUpdate(Question question) {
        if (question.getId() == null ){
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, questionExample);
            if (updated==0){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }


}
