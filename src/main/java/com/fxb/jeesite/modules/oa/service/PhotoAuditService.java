/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fxb.jeesite.common.persistence.Page;
import com.fxb.jeesite.common.service.CrudService;
import com.fxb.jeesite.modules.oa.entity.PhotoAudit;
import com.fxb.jeesite.modules.oa.dao.PhotoAuditDao;

/**
 * 照片修改申请记录Service
 * @author 方小白
 * @version 2017-07-25
 */
@Service
@Transactional(readOnly = true)
public class PhotoAuditService extends CrudService<PhotoAuditDao, PhotoAudit> {

	public PhotoAudit get(String id) {
		return super.get(id);
	}
	
	public List<PhotoAudit> findList(PhotoAudit photoAudit) {
		return super.findList(photoAudit);
	}
	
	public Page<PhotoAudit> findPage(Page<PhotoAudit> page, PhotoAudit photoAudit) {
		return super.findPage(page, photoAudit);
	}
	
	@Transactional(readOnly = false)
	public void save(PhotoAudit photoAudit) {
		super.save(photoAudit);
	}

	@Transactional(readOnly = false)
	public void delete(PhotoAudit photoAudit) {
		super.delete(photoAudit);
	}
	@Transactional(readOnly = false)
	public void update(PhotoAudit photoAudit){
		dao.update(photoAudit);
	}
	
}