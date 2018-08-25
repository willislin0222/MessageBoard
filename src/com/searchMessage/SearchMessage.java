package com.searchMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.message.model.MessageService;
import com.message.model.MessageVO;
import com.replymessage.model.ReplyMessageService;
import com.replymessage.model.ReplyMessageVO;

public class SearchMessage {
	
	public List<MessageVO> searchMessageBySeacrhtext(String searchtext,String searchselect){
		
		MemberService memberSvc = new MemberService();
		MessageService messageSvc = new MessageService();
		ReplyMessageService replymessageSvc = new ReplyMessageService();
		
		List<MemberVO> memlist =new ArrayList<MemberVO>();
		Set<MessageVO> mesSet =new HashSet<MessageVO>();
		List<MessageVO> meslist =new ArrayList<MessageVO>();
		List<ReplyMessageVO> reslist =new ArrayList<ReplyMessageVO>();
		
		if (searchselect.equals("1")){
			memlist = memberSvc.getMemberBySearchText(searchtext);
			for(MemberVO member : memlist){
				mesSet.addAll(member.getMessages());
			}
			meslist.addAll(mesSet);
			//將meslist做排序
			Collections.sort(meslist,  new Comparator<MessageVO>(){
				public int compare(MessageVO o1, MessageVO o2) {
					// TODO Auto-generated method stub
					return o2.getMes_date().compareTo(o1.getMes_date());
				}
				
			});
		}else if(searchselect.equals("2")){
			mesSet.addAll(messageSvc.getMessageBySearchText(searchtext));
			meslist.addAll(mesSet);
			//將meslist做排序
			Collections.sort(meslist,  new Comparator<MessageVO>(){
				public int compare(MessageVO o1, MessageVO o2) {
					// TODO Auto-generated method stub
					return o2.getMes_date().compareTo(o1.getMes_date());
				}
				
			});
		}else if(searchselect.equals("3")){
			memlist = memberSvc.getMemberBySearchText(searchtext);
			for(MemberVO member : memlist){
				reslist.addAll(member.getReplymessages());
			}
			for(ReplyMessageVO replymessage : reslist){
				mesSet.add(replymessage.getMessageVO());
			}
			
			meslist.addAll(mesSet);
			//將meslist做排序
			Collections.sort(meslist,  new Comparator<MessageVO>(){
				public int compare(MessageVO o1, MessageVO o2) {
					// TODO Auto-generated method stub
					return o2.getMes_date().compareTo(o1.getMes_date());
				}
				
			});
		}else if (searchselect.equals("4")){
			reslist = replymessageSvc.getReplyMessageBySearchText(searchtext);
			for(ReplyMessageVO replymessage : reslist){
				mesSet.add(replymessage.getMessageVO());
			}
			
			meslist.addAll(mesSet);
			//將meslist做排序
			Collections.sort(meslist,  new Comparator<MessageVO>(){
				public int compare(MessageVO o1, MessageVO o2) {
					// TODO Auto-generated method stub
					return o2.getMes_date().compareTo(o1.getMes_date());
				}
				
			});
			
		}else{
			meslist.addAll(messageSvc.getAll());
		}
		
		return meslist;
		
	}

}
