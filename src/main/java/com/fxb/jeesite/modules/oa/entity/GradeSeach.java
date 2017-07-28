package com.fxb.jeesite.modules.oa.entity;

import com.fxb.jeesite.common.persistence.DataEntity;

public class GradeSeach  extends DataEntity<UserInfo> {
	private Integer grade;
	private Integer peopleCount;
	private Integer cardCount;
	
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getPeopleCount() {
		return peopleCount;
	}
	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}
	public Integer getCardCount() {
		return cardCount;
	}
	public void setCardCount(Integer cardCount) {
		this.cardCount = cardCount;
	}
	@Override
	public String toString() {
		return "GradeSeach [grade=" + grade + ", peopleCount=" + peopleCount + ", cardCount=" + cardCount + "]";
	}
	
}
