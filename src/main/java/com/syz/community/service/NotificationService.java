package com.syz.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syz.community.dto.NotificationDTO;
import com.syz.community.dto.PaginationDTO;
import com.syz.community.enums.NotificationStatusEnum;
import com.syz.community.enums.NotificationTypeEnum;
import com.syz.community.exception.CustomizeErrorCode;
import com.syz.community.exception.CustomizeException;
import com.syz.community.mapper.NotificationMapper;
import com.syz.community.model.Notification;
import com.syz.community.model.NotificationExample;
import com.syz.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Resource
    NotificationMapper notificationMapper;

    @Resource
    private PaginationService paginationService;

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
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        PaginationDTO paginationDTO = paginationService.setPaginationDTO(pageInfo, notificationDTOList, pageNo);
        return paginationDTO;
    }

    public long getUnreadCount(Integer id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO readNotification(Integer id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }

    ;
}
