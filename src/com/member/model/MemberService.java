package com.member.model;

import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.message.model.MessageVO;
import com.replymessage.model.ReplyMessageVO;


public class MemberService {

	private MemberDAO_interface dao;
	public MemberService(){
		ApplicationContext context = new ClassPathXmlApplicationContext("model-config-JNDI.xml");
		dao =(MemberDAO_interface) context.getBean("memberDAO");
	}
	public void addMember(MemberVO memberVO){
		dao.insert(memberVO);
	}
	
	public void updatedMember(MemberVO memberVO){
		dao.update(memberVO);
	}
	
	public void delete(Integer mem_no){
		dao.delete(mem_no);
	}
	
	public MemberVO fingPrimaryKey(Integer mem_no){
		return dao.findPrimaryKey(mem_no);
	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();
	}
	
	public List<MemberVO> getMemberBySearchText(String searchtext){
		return dao.getMemberBySearchText(searchtext);
	}
	
	public Set<MessageVO> getMessagesByMemno(Integer mem_no){
		return dao.getMessagesByMemno(mem_no);
	}
	
	public Set<ReplyMessageVO> geReplyMessagesByMemno(Integer mem_no){
		return dao.getReplyMessagesByMemno(mem_no);
	}
	
	public MemberVO fingByMemid(String mem_id){
		return dao.findByMemid(mem_id);
	}
}
