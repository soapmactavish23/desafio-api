package com.example.api.domain.service;

import java.util.Date;

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
		
		BeanUtils.copyProperties(obj, objSaved, "id");
		
		return repository.save(objSaved);
	}
	
	public void changeStatus(Integer id) {
		BillToPay objSaved = findById(id);
		Boolean status = !objSaved.getStatus();
		objSaved.setStatus(status);
		repository.save(objSaved);
	}
	
	public Page<BillToPay> search(Boolean status, Date dueDate, String description, Pageable pageable) {
		return repository.findByStatusAndDueDateAndDescriptionContaining(status, dueDate, description, pageable);
	}
	
	public Double countByPeriod(Date startDate, Date endDate) {
		return repository.countByPeriod(startDate, endDate);
	}
	
}
