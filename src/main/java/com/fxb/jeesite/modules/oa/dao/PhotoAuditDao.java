/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.dao;

import com.fxb.jeesite.common.persistence.CrudDao;
import com.fxb.jeesite.common.persistence.annotation.MyBatisDao;
import com.fxb.jeesite.modules.oa.entity.PhotoAudit;

/**
 * 照片修改申请记录DAO接口
 * @author 方小白
 * @version 2017-07-25
 */
@MyBatisDao
public interface PhotoAuditDao extends CrudDao<PhotoAudit> {
	
}