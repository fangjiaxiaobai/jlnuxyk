/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.dao;

import com.fxb.jeesite.common.persistence.CrudDao;
import com.fxb.jeesite.common.persistence.annotation.MyBatisDao;
import com.fxb.jeesite.modules.oa.entity.GradeSeach;
import com.fxb.jeesite.modules.oa.entity.UserInfo;

import java.util.List;

/**
 * 单表生成DAO接口
 * @author fxb
 * @version 2017-07-24
 */
@MyBatisDao
public interface UserInfoDao extends CrudDao<UserInfo> {

    List<Object> findDistrictGrade();

    int getRowCounts(GradeSeach gs);

    int getCardCounts(GradeSeach gs);
}