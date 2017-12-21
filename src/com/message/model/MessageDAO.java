package com.message.model;

import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.replymessage.model.ReplyMessageVO;

public class MessageDAO implements MessageDAO_interface{

	private static final String GET_ALL_STMT="From MessageVO";
	
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}


	@Override
	public void insert(MessageVO messageVO) {
		hibernateTemplate.save(messageVO);
		
	}

	@Override
	public void update(MessageVO messageVO) {
		hibernateTemplate.update(messageVO);
		
	}

	@Override
	public void delete(Integer mes_no) {
		MessageVO messageVO = new MessageVO();
//		messageVO.getReplymessages().remove(mes_no);
		messageVO.setMes_no(mes_no);
//		MessageVO messageVO = hibernateTemplate.get(MessageVO.class, mes_no);
		hibernateTemplate.delete(messageVO);
	}

	@Override
	public MessageVO findPrimaryKey(Integer mes_no) {
		MessageVO messageVO = (MessageVO) hibernateTemplate.get(MessageVO.class, mes_no);
		return messageVO;
	}

	@Override
	public List<MessageVO> getAll() {
		List<MessageVO> list = null;
		list = (List<MessageVO>) hibernateTemplate.find(GET_ALL_STMT);
		return list;
	}
	
	@Override
	public Set<ReplyMessageVO> getReplyMessagesByMemno(Integer mes_no) {
		Set<ReplyMessageVO> set = findPrimaryKey(mes_no).getReplymessages();
		return set;
	}
	
	public static void main(String[] args) {

		//EmpHibernateDAO dao = new EmpHibernateDAO();
		//為方便一般應用程式main方的測試,所以底下的model-config1-DriverManagerDataSource.xml內部dataSource設定是採用org.springframework.jdbc.datasource.DriverManagerDataSource
		ApplicationContext context = new ClassPathXmlApplicationContext("model-config-JDBC.xml");
        
        // 建立DAO物件
		MessageDAO_interface dao =(MessageDAO_interface) context.getBean("messageDAO");

		//● 新增
//
//		MessageVO messageVO1 = new MessageVO();
//		MemberVO memberVO = new MemberVO();
//		memberVO.setMem_no(9);
//		messageVO1.setMemberVO(memberVO);
//		messageVO1.setMes_title("1324");
//		messageVO1.setMes_text("5678");
//		messageVO1.setMes_date(new java.sql.Date(System.currentTimeMillis()));
//		dao.insert(messageVO1);



		//● 修改
//		MessageVO messageVO2 = new MessageVO();
//		MemberVO memberVO = new MemberVO();
//		memberVO.setMem_no(8);
//		messageVO2.setMes_no(15);
//		messageVO2.setMemberVO(memberVO);
//		messageVO2.setMes_title("aaaa");
//		messageVO2.setMes_text("bbbb");
//		messageVO2.setMes_date(new java.sql.Date(System.currentTimeMillis()));
//		dao.update(messageVO2);



		//● 刪除(小心cascade - 多方reservation.hbm.xml如果設為 cascade="all"或
		// cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除)
		dao.delete(20);



		//● 查詢-findByPrimaryKey (多方reservation.hbm.xml必須設為lazy="false")(優!)
//		MessageVO messageVO3 = dao.findPrimaryKey(14);
//		System.out.print(messageVO3.getMes_no() + ",");
//		System.out.print(messageVO3.getMemberVO().getMem_id() + ",");
//		System.out.print(messageVO3.getMes_title() + ",");
//		System.out.print(messageVO3.getMes_text() + ",");
//		System.out.print(messageVO3.getMes_date() + ",");



		//● 查詢-getAll (多方reservation.hbm.xml必須設為lazy="false")(優!)
//		List<MessageVO> list = dao.getAll();
//		for (MessageVO amessage : list) {
//			System.out.print(amessage.getMes_no() + ",");
//			System.out.print(amessage.getMemberVO().getMem_id() + ",");
//			System.out.print(amessage.getMes_title() + ",");
//			System.out.print(amessage.getMes_text() + ",");
//			System.out.print(amessage.getMes_date() + ",");
//		}


	}




}
