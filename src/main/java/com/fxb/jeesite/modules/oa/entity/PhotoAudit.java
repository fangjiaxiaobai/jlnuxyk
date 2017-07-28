/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.fxb.jeesite.common.persistence.DataEntity;

/**
 * 照片修改申请记录Entity
 * @author 方小白
 * @version 2017-07-25
 */
public class PhotoAudit extends DataEntity<PhotoAudit> {
	
	private static final long serialVersionUID = 1L;
	private String idcard;		// idcard
	private String xykid;		// xykid
	private String auditstats;		// auditstats
	private String auditreason;		// auditreason
	private String photoOld;		// photo_old
	private String photoNew;		// photo_new
	private Date time;		// time
	
	public PhotoAudit() {
		super();
	}

	public PhotoAudit(String id){
		super(id);
	}

	@Length(min=0, max=24, message="idcard长度必须介于 0 和 24 之间")
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Length(min=0, max=16, message="xykid长度必须介于 0 和 16 之间")
	public String getXykid() {
		return xykid;
	}

	public void setXykid(String xykid) {
		this.xykid = xykid;
	}
	
	@Length(min=0, max=4, message="auditstats长度必须介于 0 和 4 之间")
	public String getAuditstats() {
		return auditstats;
	}

	public void setAuditstats(String auditstats) {
		this.auditstats = auditstats;
	}
	
	@Length(min=0, max=11, message="auditreason长度必须介于 0 和 11 之间")
	public String getAuditreason() {
		return auditreason;
	}

	public void setAuditreason(String auditreason) {
		this.auditreason = auditreason;
	}
	
	@Length(min=0, max=128, message="photo_old长度必须介于 0 和 128 之间")
	public String getPhotoOld() {
		return photoOld;
	}

	public void setPhotoOld(String photoOld) {
		this.photoOld = photoOld;
	}
	
	@Length(min=0, max=128, message="photo_new长度必须介于 0 和 128 之间")
	public String getPhotoNew() {
		return photoNew;
	}

	public void setPhotoNew(String photoNew) {
		this.photoNew = photoNew;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="time不能为空")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}