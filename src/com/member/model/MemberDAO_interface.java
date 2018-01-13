package com.member.model;

import java.util.List;
import java.util.Set;

import com.message.model.MessageVO;
import com.replymessage.model.ReplyMessageVO;

public interface MemberDAO_interface {
	public void insert(MemberVO memberVO);
	public void update(MemberVO memberVO);
	public void delete(Integer mem_no);
	public MemberVO findPrimaryKey(Integer mem_no);
	public List<MemberVO> getAll();
	
	//從會員編號取得個人資料
	public MemberVO findByMemid(String mem_id);
	
	//查詢會員發布的留言紀錄(一對多)(回傳 Set)
    public Set<MessageVO> getMessagesByMemno(Integer mem_no);
    
    //查詢會員回復的留言紀錄(一對多)(回傳 Set)
    public Set<ReplyMessageVO> getReplyMessagesByMemno(Integer mem_no);
}
