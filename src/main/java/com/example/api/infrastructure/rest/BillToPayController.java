package com.example.api.infrastructure.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.model.BillToPay;
import com.example.api.domain.service.BillToPayService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/contas-a-pagar")
public class BillToPayController {

	@Autowired
	private BillToPayService service;
	
	@PostMapping
	public ResponseEntity<BillToPay> create(@RequestBody @Valid BillToPay obj) {
		BillToPay objSaved = service.create(obj);
		return ResponseEntity.status(HttpStatus.CREATED).body(objSaved);
	}
	
	@PutMapping
	public ResponseEntity<BillToPay> update(@RequestBody @Valid BillToPay obj) {
		BillToPay objSaved = service.update(obj);
		return ResponseEntity.ok(objSaved);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BillToPay> findById(@PathVariable("id") Integer id) {
		BillToPay objSaved = service.findById(id);
		return ResponseEntity.ok(objSaved);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void changeStatus(@PathVariable("id") Integer id) {
		service.changeStatus(id);
	}
	
	@GetMapping
	public Page<BillToPay> search(@RequestParam(defaultValue = "false") Boolean status, Date dueDate,@RequestParam(defaultValue = "") String description, Pageable pageable) {
		return service.search(status, dueDate, description, pageable);
	}
	
	@GetMapping("/contar-por-periodo")
	public ResponseEntity<Double> countByPeriod(@RequestParam Date startDate,@RequestParam Date endDate) {
		Double value = service.countByPeriod(startDate, endDate);
		return ResponseEntity.ok(value);
	}
	
	
}
