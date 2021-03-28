package com.syz.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syz.community.dto.NotificationDTO;
import com.syz.community.dto.PaginationDTO;
import com.syz.community.enums.NotificationTypeEnum;
import com.syz.community.mapper.NotificationMapper;
import com.syz.community.model.Notification;
import com.syz.community.model.NotificationExample;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Resource
    NotificationMapper notificationMapper;

    public PaginationDTO getMyNotification(Integer accountId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(accountId);
        notificationExample.setOrderByClause("gmt_create DESC");
        List<Notification> notificationList = notificationMapper.selectByExample(notificationExample);
        PageInfo<Notification> pageInfo = new PageInfo<>(notificationList);
        PaginationDTO paginationDTO = getPaginationDTO(pageInfo, pageNo);
        return paginationDTO;
    }

    public PaginationDTO getPaginationDTO(PageInfo<Notification> pageInfo, int pageNo) {
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : pageInfo.getList()) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setType(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        int totalPage = (int) pageInfo.getPages();
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageNo > totalPage) {
            pageNo = totalPage;
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(notificationDTOList);
        paginationDTO.setShowPrevious(pageInfo.isHasPreviousPage());
        paginationDTO.setShowFirstPage(!(pageInfo.isIsFirstPage()));
        paginationDTO.setShowNext(pageInfo.isHasNextPage());
        paginationDTO.setShowEndPage(!(pageInfo.isIsLastPage()));
        paginationDTO.setPagination(totalPage, pageNo);
        return paginationDTO;
    }

    public long getUnreadCount(Integer id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        return  notificationMapper.countByExample(notificationExample);
    }

    ;
}
