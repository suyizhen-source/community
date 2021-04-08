package com.syz.community.schedule;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syz.community.cache.HotTagCache;
import com.syz.community.mapper.QuestionMapper;
import com.syz.community.model.Question;
import com.syz.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HotTagTasks {

    @Resource
    private  QuestionMapper questionMapper;

    @Resource
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 20000)
    public void hotTagSchedule(){
        int offset=0;
        int limit=20;
        List<Question> questionList = new ArrayList<>();
        Map<String,Integer> priorities =new HashMap<>();
        while (offset==0 || questionList.size()==limit){
            PageHelper.offsetPage(offset,limit);
            List<Question> questions = questionMapper.selectByExample(new QuestionExample());
            PageInfo<Question> pageInfo = new PageInfo<>(questions);
            for (Question question:pageInfo.getList()) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag:tags) {
                    Integer priority = priorities.get(tag);
                    if (priority!=null){
                        priorities.put(tag,priority + 5 + question.getCommentCount());
                    }else {
                        priorities.put(tag,5 + question.getCommentCount());
                    }
                }
            }
            offset+=limit;
        }
        priorities.forEach((k,v)->{
            System.out.print(k);
            System.out.print(":");
            System.out.print(v);
            System.out.println();
        });
        hotTagCache.updateTags(priorities);
    }
}
