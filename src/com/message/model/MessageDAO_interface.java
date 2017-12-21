package com.message.model;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.replymessage.model.ReplyMessageVO;
@Transactional
public interface MessageDAO_interface {
	public void insert(MessageVO messageVO);
	public void update(MessageVO messageVO);
	public void delete(Integer mes_no);
	public MessageVO findPrimaryKey(Integer mes_no);
	public List<MessageVO> getAll();
	
	//查詢留言所有回復留言紀錄(一對多)(回傳 Set)
    public Set<ReplyMessageVO> getReplyMessagesByMemno(Integer mes_no);
}
