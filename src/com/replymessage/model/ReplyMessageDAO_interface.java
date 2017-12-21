package com.replymessage.model;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface ReplyMessageDAO_interface {
	public void insert(ReplyMessageVO replyMessageVO);
	public void updatet(ReplyMessageVO replyMessageVO);
	public void delete(Integer rep_no);
	public ReplyMessageVO findPrimaryKey(Integer rep_no);
	public List<ReplyMessageVO> getAll();
}
