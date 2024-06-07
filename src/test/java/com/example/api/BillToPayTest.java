package com.example.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.api.domain.model.BillToPay;
import com.example.api.domain.service.BillToPayService;

@SpringBootTest
public class BillToPayTest {

	@Autowired
	private BillToPayService service;
	
	@Test
	public void contextLoads() {
		
		BillToPay obj = create();
		BillToPay objSaved = update(obj);
		delete(objSaved.getId());
		
	}
	
	private BillToPay create() {
		BillToPay obj = new BillToPay();
		obj.setDescription("Test");
		obj.setDueDate(LocalDate.now());
		obj.setPayDay(LocalDate.now());
		obj.setValue(200.0);
		
		BillToPay objSaved = service.create(obj);
		
		assertThat(obj).isEqualTo(objSaved);
		
		return objSaved;
	}
	
	private BillToPay update(BillToPay obj) {
		
		obj.setDescription("Test 2");
		obj.setDueDate(LocalDate.now());
		obj.setPayDay(LocalDate.now());
		obj.setValue(400.0);
		
		BillToPay objSaved = service.update(obj);
		
		assertThat(obj).isEqualTo(objSaved);
		
		return objSaved;
		
	}
	
	private void delete(Integer id) {
		service.delete(id);
	}
	
}
