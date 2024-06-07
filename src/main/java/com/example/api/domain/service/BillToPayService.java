package com.example.api.domain.service;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.domain.model.BillToPay;
import com.example.api.domain.repository.BillToPayRepository;

@Service
public class BillToPayService {

	@Autowired
	private BillToPayRepository repository;
	
	public BillToPay create(BillToPay obj) {
		return repository.save(obj);
	}
	
	public BillToPay findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
	public BillToPay update(BillToPay obj) {
		BillToPay objSaved = findById(obj.getId());
		
		BeanUtils.copyProperties(obj, objSaved, "id", "status");
		
		return repository.save(objSaved);
	}
	
	public void changeStatus(Integer id) {
		BillToPay objSaved = findById(id);
		Boolean status = !objSaved.getStatus();
		objSaved.setStatus(status);
		repository.save(objSaved);
	}
	
	public Page<BillToPay> search(String statusStr, String dueDateStr, String description, Pageable pageable) {
		
		LocalDate dueDate = LocalDate.parse(dueDateStr);
		Boolean status = Boolean.parseBoolean(statusStr);
		
		return repository.findByStatusAndDueDateAndDescriptionContaining(status, dueDate, description, pageable);
	}
	
	public Double countByPeriod(String startDateStr, String endDateStr) {
		if(!startDateStr.equals("") && !endDateStr.equals("")) {
			LocalDate startDate = LocalDate.parse(startDateStr);
			LocalDate endDate = LocalDate.parse(endDateStr);
			return repository.countByPeriod(startDate, endDate);
		} else {
			return 0.0;
		}		
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	
}
