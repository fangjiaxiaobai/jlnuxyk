/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fxb.jeesite.common.config.Global;
import com.fxb.jeesite.common.persistence.Page;
import com.fxb.jeesite.common.web.BaseController;
import com.fxb.jeesite.common.utils.StringUtils;
import com.fxb.jeesite.modules.oa.entity.PhotoAudit;
import com.fxb.jeesite.modules.oa.service.PhotoAuditService;

/**
 * 照片修改申请记录Controller
 * @author 方小白
 * @version 2017-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/photoAudit")
public class PhotoAuditController extends BaseController {

	@Autowired
	private PhotoAuditService photoAuditService;
	
	@ModelAttribute
	public PhotoAudit get(@RequestParam(required=false) String id) {
		PhotoAudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = photoAuditService.get(id);
		}
		if (entity == null){
			entity = new PhotoAudit();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:photoAudit:view")
	@RequestMapping(value = {"list", ""})
	public String list(PhotoAudit photoAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PhotoAudit> page = photoAuditService.findPage(new Page<PhotoAudit>(request, response), photoAudit); 
		model.addAttribute("page", page);
		return "modules/oa/photoAuditList";
	}

	@RequiresPermissions("oa:photoAudit:view")
	@RequestMapping(value = "form")
	public String form(PhotoAudit photoAudit, Model model) {
		model.addAttribute("photoAudit", photoAudit);
		return "modules/oa/photoAuditForm";
	}

	@RequiresPermissions("oa:photoAudit:edit")
	@RequestMapping(value = "save")
	public String save(PhotoAudit photoAudit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, photoAudit)){
			return form(photoAudit, model);
		}
		photoAuditService.save(photoAudit);
		addMessage(redirectAttributes, "保存照片修改申请记录成功");
		return "redirect:"+Global.getAdminPath()+"/oa/photoAudit/?repage";
	}
	
	@RequiresPermissions("oa:photoAudit:edit")
	@RequestMapping(value = "delete")
	public String delete(PhotoAudit photoAudit, RedirectAttributes redirectAttributes) {
		photoAuditService.delete(photoAudit);
		addMessage(redirectAttributes, "删除照片修改申请记录成功");
		return "redirect:"+Global.getAdminPath()+"/oa/photoAudit/?repage";
	}


	@RequestMapping("egis")
	@RequiresPermissions("oa:photoAudit:view")
	@ResponseBody
	public void egis(PhotoAudit photoAudit){
		photoAudit.setAuditstats("1");
		photoAuditService.update(photoAudit);
	}

	@RequestMapping("unEgis")
	@RequiresPermissions("oa:photoAudit:view")
	@ResponseBody
	public void unEgis(PhotoAudit photoAudit){
		photoAudit.setAuditstats("2");
		photoAuditService.update(photoAudit);
	}

}