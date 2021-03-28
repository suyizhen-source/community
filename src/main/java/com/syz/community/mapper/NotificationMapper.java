package com.syz.community.mapper;

import com.syz.community.model.Notification;
import com.syz.community.model.NotificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NotificationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    long countByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    int deleteByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    int insert(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    int insertSelective(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    List<Notification> selectByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    Notification selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    int updateByExampleSelective(@Param("record") Notification record, @Param("example") NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    int updateByExample(@Param("record") Notification record, @Param("example") NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    int updateByPrimaryKeySelective(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table NOTIFICATION
     *
     * @mbg.generated Sun Mar 28 19:34:43 JST 2021
     */
    int updateByPrimaryKey(Notification record);
}