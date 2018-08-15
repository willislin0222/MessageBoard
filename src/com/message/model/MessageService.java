package com.message.model;

import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.member.model.MemberDAO_interface;
import com.replymessage.model.ReplyMessageVO;

public class MessageService {

	private MessageDAO_interface dao;
	public MessageService(){
		ApplicationContext context = new ClassPathXmlApplicationContext("model-config-JNDI.xml");
		dao =(MessageDAO_interface) context.getBean("messageDAO");
	}
	
	public void addMessage(MessageVO messageVO){
		dao.insert(messageVO);
	}
	
	public void updateMessage(MessageVO messageVO){
		dao.update(messageVO);
	}
	
	public void delete(Integer mes_no){
		dao.delete(mes_no);
	}
	public MessageVO findPrimaryKey(Integer mes_no){
		return dao.findPrimaryKey(mes_no);
	}
	
	public List<MessageVO> getAll(){
		return dao.getAll();
	}
	
	public List<MessageVO> getMessageBySearchText(String searchtext){
		return dao.getMessageBySearchText(searchtext);
	}
	
	public Set<ReplyMessageVO> getReplyMessagesByMemno(Integer mes_no) {
		return dao.getReplyMessagesByMemno(mes_no);
	}
}
