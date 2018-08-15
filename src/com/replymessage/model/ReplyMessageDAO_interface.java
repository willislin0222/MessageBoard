package com.replymessage.model;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.message.model.MessageVO;
@Transactional
public interface ReplyMessageDAO_interface {
	public void insert(ReplyMessageVO replyMessageVO);
	public void updatet(ReplyMessageVO replyMessageVO);
	public void delete(Integer rep_no);
	public ReplyMessageVO findPrimaryKey(Integer rep_no);
	public List<ReplyMessageVO> getAll();
	
	//透過回覆留言內容搜尋所有相關留言
    public List<ReplyMessageVO> getReplyMessageBySearchText(String searchtext);
}
