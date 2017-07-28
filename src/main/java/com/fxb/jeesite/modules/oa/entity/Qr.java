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
 * 二维码Entity
 * @author fxb
 * @version 2017-07-24
 */
public class Qr extends DataEntity<Qr> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 此次生成二维码的标识，系统生成
	private Date time;		// 生成时间
	private String startid;		// 开始编号
	private String endid;		// 结束编号
	private String filepath;		// 存储路径
	private String filename;		// 文件名称
	
	public Qr() {
		super();
	}

	public Qr(String id){
		super(id);
	}

	@Length(min=1, max=128, message="此次生成二维码的标识，系统生成长度必须介于 1 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="生成时间不能为空")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@Length(min=1, max=8, message="开始编号长度必须介于 1 和 8 之间")
	public String getStartid() {
		return startid;
	}

	public void setStartid(String startid) {
		this.startid = startid;
	}
	
	@Length(min=1, max=8, message="结束编号长度必须介于 1 和 8 之间")
	public String getEndid() {
		return endid;
	}

	public void setEndid(String endid) {
		this.endid = endid;
	}
	
	@Length(min=1, max=128, message="存储路径长度必须介于 1 和 128 之间")
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	@Length(min=0, max=128, message="文件名称长度必须介于 0 和 128 之间")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}