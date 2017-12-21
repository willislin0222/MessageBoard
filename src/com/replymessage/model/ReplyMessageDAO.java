package com.replymessage.model;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.member.model.MemberVO;
import com.message.model.MessageVO;

public class ReplyMessageDAO implements ReplyMessageDAO_interface{

	private static final String GET_ALL_STMT="From ReplyMessageVO";
	
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void insert(ReplyMessageVO replyMessageVO) {
		hibernateTemplate.save(replyMessageVO);
		
	}

	@Override
	public void updatet(ReplyMessageVO replyMessageVO) {
		hibernateTemplate.update(replyMessageVO);
		
	}

	@Override
	public void delete(Integer rep_no) {
		ReplyMessageVO replyMessageVO = new  ReplyMessageVO();
		replyMessageVO.setRep_no(rep_no);
		hibernateTemplate.delete(replyMessageVO);
		
	}

	@Override
	public ReplyMessageVO findPrimaryKey(Integer rep_no) {
		ReplyMessageVO replyMessageVO = (ReplyMessageVO) hibernateTemplate.get(ReplyMessageVO.class, rep_no);
		return replyMessageVO;
	}

	@Override
	public List<ReplyMessageVO> getAll() {
		List<ReplyMessageVO> list = null;
		list = (List<ReplyMessageVO>) hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}
	
	public static void main(String[] args) {

		//EmpHibernateDAO dao = new EmpHibernateDAO();
		//為方便一般應用程式main方的測試,所以底下的model-config1-DriverManagerDataSource.xml內部dataSource設定是採用org.springframework.jdbc.datasource.DriverManagerDataSource
		ApplicationContext context = new ClassPathXmlApplicationContext("model-config-JDBC.xml");
        
        // 建立DAO物件
		ReplyMessageDAO_interface dao =(ReplyMessageDAO_interface) context.getBean("replymessageDAO");

		//● 新增
//
//		ReplyMessageVO replymessageVO1 = new ReplyMessageVO();
//		MemberVO memberVO = new MemberVO();
//		memberVO.setMem_no(2);
//		MessageVO messageVO = new MessageVO();
//		messageVO.setMes_no(33);
//		replymessageVO1.setMemberVO(memberVO);
//		replymessageVO1.setMessageVO(messageVO);
//		replymessageVO1.setRep_text("5678");
//		replymessageVO1.setRep_date(new java.sql.Date(System.currentTimeMillis()));
//		dao.insert(replymessageVO1);



		//● 修改
//		ReplyMessageVO replymessageVO2 = new ReplyMessageVO();
//		replymessageVO2.setRep_no(10);
//		MemberVO memberVO = new MemberVO();
//		memberVO.setMem_no(1);
//		MessageVO messageVO = new MessageVO();
//		messageVO.setMes_no(17);
//		replymessageVO2.setMemberVO(memberVO);
//		replymessageVO2.setMessageVO(messageVO);
//		replymessageVO2.setRep_text("aaaa");
//		replymessageVO2.setRep_date(new java.sql.Date(System.currentTimeMillis()));
//		dao.updatet(replymessageVO2);



		//● 刪除(小心cascade - 多方reservation.hbm.xml如果設為 cascade="all"或
		// cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除)
//		dao.delete(28);



		//● 查詢-findByPrimaryKey (多方reservation.hbm.xml必須設為lazy="false")(優!)
//		ReplyMessageVO replymessageVO3 = dao.findPrimaryKey(9);
//		System.out.print(replymessageVO3.getRep_no() + ",");
//		System.out.print(replymessageVO3.getMemberVO().getMem_id() + ",");
//		System.out.print(replymessageVO3.getMessageVO().getMes_title() + ",");
//		System.out.print(replymessageVO3.getRep_text() + ",");
//		System.out.print(replymessageVO3.getRep_date() + ",");



		//● 查詢-getAll (多方reservation.hbm.xml必須設為lazy="false")(優!)
//		List<ReplyMessageVO> list = dao.getAll();
//		for (ReplyMessageVO replyamessage : list) {
//			System.out.print(replyamessage.getRep_no() + ",");
//			System.out.print(replyamessage.getMemberVO().getMem_id() + ",");
//			System.out.print(replyamessage.getMessageVO().getMes_title() + ",");
//			System.out.print(replyamessage.getRep_text() + ",");
//			System.out.print(replyamessage.getRep_date() + ",");
//
//		}

	}
}
