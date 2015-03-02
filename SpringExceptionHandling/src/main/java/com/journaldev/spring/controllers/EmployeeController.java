package com.journaldev.spring.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.journaldev.spring.exceptions.EmployeeNotFoundException;
import com.journaldev.spring.exceptions.ExampleException;
import com.journaldev.spring.model.Employee;
import com.journaldev.spring.model.ExceptionJSONInfo;
import com.journaldev.spring.model.Product;


@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@ModelAttribute("product")
	public Product constructProduct() {
		return new Product();
	}
	
	@RequestMapping(value="/emp/{id}", method=RequestMethod.GET)
	public ModelAndView getEmployee(@PathVariable("id") int id, Model model) throws Exception{
		//deliberately throwing different types of exception
		if(id==1){
			throw new EmployeeNotFoundException(id);
		}else if(id==2){
			throw new SQLException("SQLException, id="+id);
		}else if(id==3){
			throw new IOException("IOException, id="+id);
		}
		else if(id==5){
			return new ModelAndView("product");
		}
		else if(id==10){
			Employee emp = new Employee();
			emp.setName("Pankaj");
			emp.setId(id);
			model.addAttribute("employee", emp);
			return new ModelAndView("home");
		}else {
			throw new Exception("Generic Exception, id="+id);
		}
		
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	@ExceptionHandler(ExampleException.class)
	public String addProduct(@ModelAttribute("product") Product product,
			ModelMap model) {
		System.out.println("add product");
		if (product.getName().length() < 5) {
			throw new ExampleException("Product Name entered is too short");
		} else {
			model.addAttribute("name", product.getName());
		}
		System.out.println("sjdnln");
		return "result";
	}

	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ModelAndView handleEmployeeNotFoundException1(HttpServletRequest request, Exception ex){
		logger.error("Requested URL="+request.getRequestURL());
		logger.error("Exception Raised="+ex);
		System.out.println("request.getRequestURL() "+request.getRequestURL());
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("error");
	    return modelAndView;
	}
	
//	@ExceptionHandler(EmployeeNotFoundException.class)
//	public @ResponseBody ExceptionJSONInfo handleEmployeeNotFoundException(HttpServletRequest request, Exception ex){
//		
//		ExceptionJSONInfo response = new ExceptionJSONInfo();
//		response.setUrl(request.getRequestURL().toString());
//		response.setMessage(ex.getMessage());
//		
//		return response;
//	}
	
}
