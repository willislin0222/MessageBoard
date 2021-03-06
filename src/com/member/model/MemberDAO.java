package com.member.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.message.model.MessageVO;
import com.replymessage.model.ReplyMessageVO;

public class MemberDAO implements MemberDAO_interface{

	private static final String GET_ALL_STMT="From MemberVO";
	private static final String GET_ONE_BY_MEMID="From MemberVO where mem_id=?";
	private static final String GET_ALL_BY_MEMID="From MemberVO where mem_id like ?";
	
	//spring hibernateTemplate
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void insert(MemberVO memberVO) {
		hibernateTemplate.save(memberVO);
		
	}

	@Override
	public void update(MemberVO memberVO) {
		hibernateTemplate.update(memberVO);
		
	}

	@Override
	public void delete(Integer mem_no) {
		MemberVO memberVO = (MemberVO) hibernateTemplate.get(MemberVO.class, mem_no);
		hibernateTemplate.delete(memberVO);
	}

	@Override
	public MemberVO findPrimaryKey(Integer mem_no) {
		MemberVO memberVO = (MemberVO) hibernateTemplate.get(MemberVO.class, mem_no);
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = null;
		list = (List<MemberVO>) hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}
	
	@Override
	public Set<MessageVO> getMessagesByMemno(Integer mem_no) {
		Set<MessageVO> set = findPrimaryKey(mem_no).getMessages();
		return set;
	}

	@Override
	public Set<ReplyMessageVO> getReplyMessagesByMemno(Integer mem_no) {
		Set<ReplyMessageVO> set = findPrimaryKey(mem_no).getReplymessages();
		return null;
	}
	
	@Override
	public MemberVO findByMemid(String mem_id) {
		List<MemberVO> list = new LinkedList<MemberVO>();
		MemberVO memberVO = new MemberVO();
		list = (List<MemberVO>) hibernateTemplate.find(GET_ONE_BY_MEMID, mem_id);
		for(MemberVO amember:list){
			memberVO = amember;
		}
		return memberVO;
	}
	
	@Override
	public List<MemberVO> getMemberBySearchText(String searchtext) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(GET_ALL_BY_MEMID);
		query.setParameter(0, "%" + searchtext + "%");
		list = query.list();
		return list;
	}
	
	public static void main(String[] args) {

		//EmpHibernateDAO dao = new EmpHibernateDAO();
		//為方便一般應用程式main方的測試,所以底下的model-config1-DriverManagerDataSource.xml內部dataSource設定是採用org.springframework.jdbc.datasource.DriverManagerDataSource
		ApplicationContext context = new ClassPathXmlApplicationContext("model-config-JDBC.xml");
        
        // 建立DAO物件
		MemberDAO_interface dao =(MemberDAO_interface) context.getBean("memberDAO");

		//● 新增
//
//		MemberVO memberVO1 = new MemberVO();
//		memberVO1.setMem_id("vicky");
//		memberVO1.setMem_psw("0823");
//		memberVO1.setMem_joindate(new java.sql.Date(System.currentTimeMillis()));
//		dao.insert(memberVO1);



		//● 修改
//		MemberVO memberVO2 = new MemberVO();
//		memberVO2.setMem_no(7);
//		memberVO2.setMem_id("uao");
//		memberVO2.setMem_psw("1227");
//		memberVO2.setMem_joindate(new java.sql.Date(System.currentTimeMillis()));
//		dao.update(memberVO2);



		//● 刪除(小心cascade - 多方reservation.hbm.xml如果設為 cascade="all"或
		// cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除)
//		dao.delete(11);



		//● 查詢-findByPrimaryKey (多方reservation.hbm.xml必須設為lazy="false")(優!)
//		MemberVO memberVO3 = dao.findPrimaryKey(2);
//		System.out.print(memberVO3.getMem_no() + ",");
//		System.out.print(memberVO3.getMem_id() + ",");
//		System.out.print(memberVO3.getMem_psw() + ",");
//		System.out.print(memberVO3.getMem_joindate() + ",");
//		System.out.print(memberVO3.getMem_photo() + ",");



		//● 查詢-getAll (多方reservation.hbm.xml必須設為lazy="false")(優!)
//		List<MemberVO> list = dao.getAll();
//		for (MemberVO amember : list) {
//			System.out.print(amember.getMem_no() + ",");
//			System.out.print(amember.getMem_id() + ",");
//			System.out.print(amember.getMem_psw() + ",");
//			System.out.print(amember.getMem_joindate() + ",");
//			System.out.print(amember.getMem_photo() + ",");
//		}
		////取得此會員所有發布的留言
//		Set<MessageVO> set=dao.findPrimaryKey(3).getMessages();
//		for (MessageVO amessage : set) {
//			System.out.print(amessage.getMes_no() + ",");
//			System.out.print(amessage.getMemberVO().getMem_id() + ",");
//			System.out.print(amessage.getMes_title() + ",");
//			System.out.print(amessage.getMes_text() + ",");
//			System.out.print(amessage.getMes_date() + ",");
//		}
		//取得此會員所有回復資料
//		Set<ReplyMessageVO> set2=dao.findPrimaryKey(1).getReplymessages();
//		for (ReplyMessageVO areplymessage : set2) {
//		System.out.print(areplymessage.getRep_no() + ",");
//		System.out.print(areplymessage.getMemberVO().getMem_id() + ",");
//		System.out.print(areplymessage.getMessageVO().getMes_title() + ",");
//		System.out.print(areplymessage.getRep_text() + ",");
//		System.out.print(areplymessage.getRep_date() + ",");
//		}
	}



}
