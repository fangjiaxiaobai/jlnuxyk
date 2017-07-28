/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.service;

import com.fxb.jeesite.common.persistence.Page;
import com.fxb.jeesite.common.service.CrudService;
import com.fxb.jeesite.modules.oa.dao.UserInfoDao;
import com.fxb.jeesite.modules.oa.entity.GradeSeach;
import com.fxb.jeesite.modules.oa.entity.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 单表生成Service
 *
 * @author fxb
 * @version 2017-07-24
 */
@Service
@Transactional(readOnly = true)
public class UserInfoService extends CrudService<UserInfoDao, UserInfo> {

    public UserInfo get(String id) {
        return super.get(id);
    }

    public List<UserInfo> findList(UserInfo userInfo) {
        return super.findList(userInfo);
    }

    public Page<UserInfo> findPage(Page<UserInfo> page, UserInfo userInfo) {
        return super.findPage(page, userInfo);
    }

    @Transactional(readOnly = false)
    public void save(UserInfo userInfo) {
        super.save(userInfo);
    }

    @Transactional(readOnly = false)
    public void delete(String[] ids) {
        for (String id : ids) {
            if(null!=id) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(id);
                super.delete(userInfo);
            }
        }
    }

    @Transactional
    public void restore(String[] ids, int flag) {
        for (String i : ids) {
            UserInfo user = new UserInfo();
            user.setId(i);
            user.setFlag(flag + "");
            super.restore(user);
        }
    }

    @Transactional
    public List<GradeSeach> gradeSeach(GradeSeach gradeSeach) {
        List<GradeSeach> gradeDatas = new ArrayList<GradeSeach>();
        List<Object> grades = this.dao.findDistrictGrade();
        for (Object obj : grades) {
            if(null != obj){
                int grade = (Integer)obj;
                GradeSeach gs = new GradeSeach();
                gs.setGrade(grade);
                int rowCounts = this.dao.getRowCounts(gs);
                gs.setPeopleCount(rowCounts);
                int carCounts = this.dao.getCardCounts(gs);
                gs.setCardCount(carCounts);
                gradeDatas.add(gs);
            }

        }
        return gradeDatas;
    }

    @Transactional
    public String batchSave(List<UserInfo> userInfos) {
        StringBuilder msg = new StringBuilder("导入信息：");
        int count = 0;
        int errorCount = 0;
        for (UserInfo userInfo : userInfos) {
            try {
                super.save(userInfo);
                count++;
            } catch (Exception e) {
                if(errorCount<10) {
                    msg.append(userInfo.getXykid() + ",");
                }else if(errorCount==10){
                    msg.append(userInfo.getXykid() + "等,");
                }
                errorCount++;
                continue;
            }
        }
        msg.append("共计"+errorCount + "条信息导入失败," + errorCount + "条信息导入成功");
        return msg.toString();
    }
}