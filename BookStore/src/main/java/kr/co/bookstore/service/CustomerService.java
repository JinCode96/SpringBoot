package kr.co.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.bookstore.dao.CustomerDAO;
import kr.co.bookstore.vo.CustomerVO;

@Service
public class CustomerService {
	
	@Autowired 
	private CustomerDAO dao;
	
	public void insertCust(CustomerVO vo) {
		dao.insertCust(vo);
	}
	
	public CustomerVO selectCust(int custId) {
		return dao.selectCust(custId);
	}
	
	public List<CustomerVO> selectCusts() {
		return dao.selectCusts();
	}
	
	public void updateCust(CustomerVO vo) {
		dao.updateCust(vo);
	}
	
	public void deleteCust(int custId) {
		dao.deleteCust(custId);
	}
}
