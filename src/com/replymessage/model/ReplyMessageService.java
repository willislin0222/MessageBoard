package com.replymessage.model;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.message.model.MessageDAO_interface;

public class ReplyMessageService {

	private ReplyMessageDAO_interface dao;
	public ReplyMessageService(){
		ApplicationContext context = new ClassPathXmlApplicationContext("model-config-JNDI.xml");
		dao =(ReplyMessageDAO_interface) context.getBean("replymessageDAO");
	}
	
	public void addReplyMessage(ReplyMessageVO replyMessageVO){
		dao.insert(replyMessageVO);
	}
	
	public void updateReplyMessage(ReplyMessageVO replyMessageVO){
		dao.updatet(replyMessageVO);
	}
	
	public void delete(Integer rep_no){
		dao.delete(rep_no);
	}
	
	public ReplyMessageVO findPrimaryKey(Integer rep_no){
		return dao.findPrimaryKey(rep_no);
	}
	
	public List<ReplyMessageVO> getAll(){
		return dao.getAll();
	}
	
	public List<ReplyMessageVO> getReplyMessageBySearchText(String searchtext){
		return dao.getReplyMessageBySearchText(searchtext);
	}
	
}
