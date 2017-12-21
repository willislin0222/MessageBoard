package com.message.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.member.model.MemberVO;
import com.replymessage.model.ReplyMessageVO;

public class MessageVO {
	private Integer mes_no;
	private MemberVO memberVO;
	private String mes_title;
	private String mes_text;
	private Date mes_date;
	
	//取得會員回復的全部留言
	private Set<ReplyMessageVO> replymessages = new HashSet<ReplyMessageVO>();
	
	public Integer getMes_no() {
		return mes_no;
	}
	public void setMes_no(Integer mes_no) {
		this.mes_no = mes_no;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public String getMes_title() {
		return mes_title;
	}
	public void setMes_title(String mes_title) {
		this.mes_title = mes_title;
	}
	public String getMes_text() {
		return mes_text;
	}
	public void setMes_text(String mes_text) {
		this.mes_text = mes_text;
	}
	public Date getMes_date() {
		return mes_date;
	}
	public void setMes_date(Date mes_date) {
		this.mes_date = mes_date;
	}
	public Set<ReplyMessageVO> getReplymessages() {
		return replymessages;
	}
	public void setReplymessages(Set<ReplyMessageVO> replymessages) {
		this.replymessages = replymessages;
	}
	
	

	
}
