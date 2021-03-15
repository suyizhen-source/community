package com.syz.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syz.community.dto.PaginationDTO;
import com.syz.community.dto.QuestionDto;
import com.syz.community.mapper.QuestionMapper;
import com.syz.community.mapper.UserMapper;
import com.syz.community.pojo.Question;
import com.syz.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDTO getQuestionList(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Question> questions = questionMapper.selAllQuestion();
        PageInfo<Question>pageInfo = new PageInfo<>(questions);
        PaginationDTO paginationDTO = getPaginationDTO(pageInfo,pageNo);
        return paginationDTO;
    }

    public PaginationDTO getMyQuestionList(long accountId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Question> questions = questionMapper.selQuestionByUser(accountId);
        PageInfo<Question>pageInfo = new PageInfo<>(questions);
        PaginationDTO paginationDTO = getPaginationDTO(pageInfo,pageNo);
        return paginationDTO;
    }

    public PaginationDTO  getPaginationDTO(PageInfo<Question> pageInfo,int pageNo){
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question:pageInfo.getList()){
            User user =userMapper.findUserByAccountId(question.getCreator());
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

}
