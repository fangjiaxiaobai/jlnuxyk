/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fxb.jeesite.modules.oa.entity;

import com.fxb.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.fxb.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author fxb
 * @version 2017-07-24
 */
public class UserInfo extends DataEntity<UserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String flag;		// flag
	private String idcard;		// 身份证号
	private String name;		// 姓名
	private String xykid;		// 校园卡编号
	private String uid;		// 学号
	private String sex;		// 性别
	private String academy;		// 学院
	private String profession;		// 专业
	private String class_;		// 班级
	private String phonenumber;		// 电话
	private String photo;		// 照片路径
	private Date timeHavecard;		// 办卡时间
	private String timesCard;		// 办卡次数
	private String grade;		// 年级
	
	public UserInfo() {
		super();
	}

	public UserInfo(String id){
		super(id);
	}

	@ExcelField(title = "idcard",sort = 1)
	@Length(min=1, max=24, message="身份证号长度必须介于 1 和 24 之间")
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Length(min=1, max=16, message="姓名长度必须介于 1 和 16 之间")
	@ExcelField(title = "name",sort = 2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=12, message="校园卡编号长度必须介于 1 和 12 之间")
	@ExcelField(title = "xykid",sort = 3)
	public String getXykid() {
		return xykid;
	}

	public void setXykid(String xykid) {
		this.xykid = xykid;
	}
	
	@Length(min=1, max=16, message="学号长度必须介于 1 和 16 之间")
	@ExcelField(title = "uid",sort = 4)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=1, max=8, message="性别长度必须介于 1 和 8 之间")
	@ExcelField(title="sex",sort = 5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=1, max=32, message="学院长度必须介于 1 和 32 之间")
	@ExcelField(title="academy",sort = 6)
	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}
	
	@Length(min=1, max=32, message="专业长度必须介于 1 和 32 之间")
	@ExcelField(title="profession",sort = 7)
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	@Length(min=1, max=6, message="班级长度必须介于 1 和 6 之间")
	@ExcelField(title="class_",sort = 8)
	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}
	
	@Length(min=0, max=16, message="电话长度必须介于 0 和 16 之间")
	@ExcelField(title="photonumber",sort = 9)
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	@Length(min=0, max=128, message="照片路径长度必须介于 0 和 128 之间")
	@ExcelField(title = "photo",sort = 10)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="办卡时间不能为空")
	@ExcelField(title="timeshavecard",sort = 12)
	public Date getTimeHavecard() {
		return timeHavecard;
	}

	public void setTimeHavecard(Date timeHavecard) {
		this.timeHavecard = timeHavecard;
	}
	
	@Length(min=1, max=4, message="办卡次数长度必须介于 1 和 4 之间")
	@ExcelField(title="timescard",sort = 11)
	public String getTimesCard() {
		return timesCard;
	}

	public void setTimesCard(String timesCard) {
		this.timesCard = timesCard;
	}
	
	@Length(min=1, max=11, message="年级长度必须介于 1 和 11 之间")
	@ExcelField(title="grade",sort = 13)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min=0, max=4, message="flag长度必须介于 0 和 4 之间")
	@ExcelField(title = "flag",sort = 14)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}