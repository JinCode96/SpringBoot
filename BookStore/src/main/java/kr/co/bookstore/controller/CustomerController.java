package kr.co.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.bookstore.service.CustomerService;
import kr.co.bookstore.vo.CustomerVO;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping("/customer/list")
	public String list(Model model) {
		List<CustomerVO> customers = service.selectCusts();
		model.addAttribute("customers", customers);
		return "/customer/list";
	}
	
	@GetMapping("/customer/register")
	public String register() {
		return "customer/register";
	}
	
	@PostMapping("/customer/register")
	public String register(CustomerVO vo) {
		service.insertCust(vo);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/customer/modify")
	public String modify(int custId, Model model) {
		CustomerVO customer = service.selectCust(custId);
		model.addAttribute("customer", customer);
		return "/customer/modify";
	}
	
	@PostMapping("/customer/modify")
	public String modify(CustomerVO vo) {
		service.updateCust(vo);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/customer/delete")
	public String delete(int custId) {
		service.deleteCust(custId);
		return "redirect:/customer/list";
	}
	
	
}
