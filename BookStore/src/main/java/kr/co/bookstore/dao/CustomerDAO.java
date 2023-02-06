package kr.co.bookstore.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bookstore.vo.CustomerVO;

@Repository
public class CustomerDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertCust(CustomerVO vo) {
		mybatis.insert("cust.insertCust", vo);
	}
	
	public CustomerVO selectCust(int custId) {
		return mybatis.selectOne("cust.selectCust", custId);
	}
	
	public List<CustomerVO> selectCusts() {
		return mybatis.selectList("cust.selectCusts");
	}
	
	public void updateCust(CustomerVO vo) {
		mybatis.update("cust.updateCust", vo);
	}
	
	public void deleteCust(int custId) {
		mybatis.delete("cust.deleteCust", custId);
	}
	
}
