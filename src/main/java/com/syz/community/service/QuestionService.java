package com.syz.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syz.community.dto.PaginationDTO;
import com.syz.community.dto.QuestionDTO;
import com.syz.community.dto.QuestionQueryDTO;
import com.syz.community.enums.SortEnum;
import com.syz.community.exception.CustomizeErrorCode;
import com.syz.community.exception.CustomizeException;
import com.syz.community.mapper.QuestionExtMapper;
import com.syz.community.mapper.QuestionMapper;
import com.syz.community.mapper.UserMapper;
import com.syz.community.model.Question;
import com.syz.community.model.QuestionExample;
import com.syz.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * questionについての業務処理
 */

@Service
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionExtMapper questionExtMapper;

    @Resource
    private PaginationService paginationService;

    @Resource
    private QuestionService questionService;

    public PaginationDTO getQuestionList(String search, String tag, String sort, int pageNo, int pageSize) {
        String searchReplace = "";
        String tagReplace = "";
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        if (StringUtils.isNotBlank(search)) {
            searchReplace = StringUtils.replace(search, " ", "|").replace("+","").replace("*","").replace("?", "");
            questionQueryDTO.setSearch(searchReplace);
        }
        if (StringUtils.isNotBlank(tag)) {
            tagReplace = StringUtils.replace(tag, "+", "").replace("*","").replace("?", "");
            questionQueryDTO.setTag(tag);
        }
        for (SortEnum sortEnum:SortEnum.values()) {
            if (sortEnum.name().toLowerCase(Locale.ROOT).equals(sort)){
                questionQueryDTO.setSort(sort);
                if (sortEnum == SortEnum.HOT7) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        PaginationDTO paginationDTO = getPaginationDTO(pageInfo, pageNo);
        return paginationDTO;
    }

    public PaginationDTO getMyQuestionList(Integer accountId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(accountId);
        questionExample.setOrderByClause("gmt_create DESC");
        List<Question> questions = questionMapper.selectByExample(questionExample);
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        PaginationDTO paginationDTO = getPaginationDTO(pageInfo, pageNo);
        return paginationDTO;
    }

    public QuestionDTO getQuestionById(Integer id) {
        questionService.addViewCount(id);
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDto = getQuestionDto(question);
        return questionDto;
    }

    public QuestionDTO getQuestionDto(Question question) {
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDto = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUser(user);
        questionDto.setViewCount(question.getViewCount());
        return questionDto;
    }

    public PaginationDTO getPaginationDTO(PageInfo<Question> pageInfo, int pageNo) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : pageInfo.getList()) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDto = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDTOList.add(questionDto);
        }
        PaginationDTO paginationDTO = paginationService.setPaginationDTO(pageInfo, questionDTOList, pageNo);
        return paginationDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        } else {
            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
            if (dbQuestion==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            if (dbQuestion.getCreator()!=question.getCreator()){
                throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
            }
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(question, questionExample);
            if (updated == 0) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void addViewCount(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.addViewCount(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDto) {
        if (StringUtils.isBlank(queryDto.getTag())) {
            return new ArrayList<>();
        }
        String replace = StringUtils.replace(queryDto.getTag(), ",", "|").replace("+","").replace("*","").replace("?", "");
        Question question = new Question();
        question.setId(queryDto.getId());
        question.setTag(replace);
        List<Question> questionList = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOList = questionList.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOList;
    }
}
