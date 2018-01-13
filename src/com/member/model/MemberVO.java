package com.member.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.message.model.MessageVO;
import com.replymessage.model.ReplyMessageVO;

public class MemberVO {
	private Integer mem_no;
	private String mem_id;
	private String mem_psw;
	private Date mem_joindate;
	private byte[] mem_photo;
	//取得會員發布的全部留言
	private Set<MessageVO> messages = new HashSet<MessageVO>();
	
	//取得會員回復的全部留言
	private Set<ReplyMessageVO> replymessages = new HashSet<ReplyMessageVO>();
	
	public Integer getMem_no() {
		return mem_no;
	}
	public void setMem_no(Integer mem_no) {
		this.mem_no = mem_no;
	}
	
	@NotNull(message="會員帳號: 請勿空白")
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	
	@NotNull(message="會員密碼: 請勿空白")
	public String getMem_psw() {
		return mem_psw;
	}
	public void setMem_psw(String mem_psw) {
		this.mem_psw = mem_psw;
	}
	public Date getMem_joindate() {
		return mem_joindate;
	}
	public void setMem_joindate(Date mem_joindate) {
		this.mem_joindate = mem_joindate;
	}
	public byte[] getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(byte[] mem_photo) {
		this.mem_photo = mem_photo;
	}
	public Set<MessageVO> getMessages() {
		return messages;
	}
	public void setMessages(Set<MessageVO> messages) {
		this.messages = messages;
	}
	public Set<ReplyMessageVO> getReplymessages() {
		return replymessages;
	}
	public void setReplymessages(Set<ReplyMessageVO> replymessages) {
		this.replymessages = replymessages;
	}
	
	

	
}
