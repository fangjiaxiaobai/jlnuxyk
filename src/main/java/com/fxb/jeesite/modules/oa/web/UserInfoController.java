/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.web;

import com.fxb.jeesite.common.config.Global;
import com.fxb.jeesite.common.persistence.Page;
import com.fxb.jeesite.common.utils.StringUtils;
import com.fxb.jeesite.common.utils.excel.ExportExcel;
import com.fxb.jeesite.common.utils.excel.ImportExcel;
import com.fxb.jeesite.common.web.BaseController;
import com.fxb.jeesite.modules.oa.entity.GradeSeach;
import com.fxb.jeesite.modules.oa.entity.UserInfo;
import com.fxb.jeesite.modules.oa.service.UserInfoService;
import com.google.common.collect.Lists;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 单表生成Controller
 *
 * @author fxb
 * @version 2017-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/userInfo")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @ModelAttribute
    public UserInfo get(@RequestParam(required = false) String id) {
        UserInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userInfoService.get(id);
        }
        if (entity == null) {
            entity = new UserInfo();
        }
        return entity;
    }

    @RequiresPermissions("oa:userInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<UserInfo> page = userInfoService.findPage(new Page<UserInfo>(request, response), userInfo);
        model.addAttribute("page", page);
        if (userInfo.getFlag() != null && userInfo.getFlag().equals("0")) {
            //垃圾箱
            return "modules/oa/userInfoFreshList";
        } else {
            return "modules/oa/userInfoList";
        }
    }

    /**
     * 添加单个学生信息
     *
     * @param userInfo
     * @param model
     * @return
     */
    @RequiresPermissions("oa:userInfo:view")
    @RequestMapping(value = "form")
    public String form(UserInfo userInfo, Model model) {
        model.addAttribute("userInfo", userInfo);
        return "modules/oa/userInfoForm";
    }

    @RequiresPermissions("oa:userInfo:view")
    @RequestMapping(value = "save")
    public String save(UserInfo userInfo, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, userInfo)) {
            return form(userInfo, model);
        }
        userInfoService.save(userInfo);
        addMessage(redirectAttributes, "保存单表成功");
        return "redirect:" + Global.getAdminPath() + "/oa/userInfo/?repage";
    }

    /**
     * 对学生信息进行删除
     *
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:userInfo:view")
    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        String[] ids = id.split(",");
        userInfoService.delete(ids);
        addMessage(redirectAttributes, "删除信息成功");
        return "redirect:" + Global.getAdminPath() + "/oa/userInfo/?flag=0";
    }


    /**
     * 批量还原student,set flag=1
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("restore")
    public String restore(Model model, String id) {
        String[] ids = id.split(",");
        this.userInfoService.restore(ids, 1);
        return "redirect:" + Global.getAdminPath() + "/oa/userInfo/?flag=0";
    }

    /**
     * 把student放入垃圾箱，set flag=0
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("hypocrisyDelete")
    @RequiresPermissions("oa:userInfo:view")
    public String hypocrisyDelete(Model model, String id) {
        String[] ids = id.split(",");
        this.userInfoService.restore(ids, 0);
        return "redirect:" + Global.getAdminPath() + "/oa/userInfo/?flag=1";
    }

    /**
     * 年级管理：按年级统计学生信息的校园卡数，人数。
     * 提供删除操作
     *
     * @param gradeSeach
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("manageList")
    @RequiresPermissions("oa:userInfo:view")
    public String manageList(GradeSeach gradeSeach, Model model, HttpServletRequest request, HttpServletResponse response) {
        List<GradeSeach> grades = this.userInfoService.gradeSeach(gradeSeach);
        model.addAttribute("grade", grades);
        return "modules/oa/gradeManager";
    }

    @RequestMapping("importEXCEL")
    @RequiresPermissions("oa:userInfo:view")
    public String batchSaveFormExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            ImportExcel importExcel = new ImportExcel(file, 1, 0);
            List<UserInfo> userInfos = importExcel.getDataList(UserInfo.class);
            String msg = userInfoService.batchSave(userInfos);
            addMessage(redirectAttributes, msg);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "redirect:" + Global.getAdminPath() + "/oa/userInfo/?flag=1";
    }

    @RequestMapping("exportExcel")
    @RequiresPermissions("oa:userInfo:view")
    public String exportExcel(HttpServletResponse response, HttpServletRequest request) {
        return "";
    }


    @RequestMapping("exportTemplate")
    @RequiresPermissions("oa:userInfo:view")
    public String exportTemplate(HttpServletResponse response, RedirectAttributes  redirectAttributes,Integer type,UserInfo userInfo) {
        try {
            String fileName = "学生信息.xlsx";
            List<UserInfo>  list = null;
            if( type==2 && null!=userInfo && null!=userInfo.getFlag()){
                //下载模板
                list = Lists.newArrayList();
            }else{
//                下载所有数据
                userInfo.setFlag(1+"");
                list = userInfoService.findList(userInfo);
            }
            new ExportExcel("学生信息数据",UserInfo.class,type).setDataList(list).write(response,fileName).dispose();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirce:"+Global.getAdminPath()+"/oa/userInfo/?flag="+userInfo.getFlag();
    }


}