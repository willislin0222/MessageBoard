package com.replymessage.model;

import java.sql.Date;

import com.member.model.MemberVO;
import com.message.model.MessageVO;

public class ReplyMessageVO {
	private Integer rep_no;
	private MemberVO memberVO;
	private MessageVO messageVO;
	private String rep_text;
	private Date rep_date;
	public Integer getRep_no() {
		return rep_no;
	}
	public void setRep_no(Integer rep_no) {
		this.rep_no = rep_no;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public MessageVO getMessageVO() {
		return messageVO;
	}
	public void setMessageVO(MessageVO messageVO) {
		this.messageVO = messageVO;
	}
	public String getRep_text() {
		return rep_text;
	}
	public void setRep_text(String rep_text) {
		this.rep_text = rep_text;
	}
	public Date getRep_date() {
		return rep_date;
	}
	public void setRep_date(Date rep_date) {
		this.rep_date = rep_date;
	}
	
	
}
