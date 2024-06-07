package com.example.api.domain.service;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.domain.model.BillToPay;
import com.example.api.domain.repository.BillToPayRepository;
import com.example.api.infrastructure.exception.GenericException;
import com.opencsv.CSVReader;

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
	
	public List<BillToPay> importCsv(MultipartFile file) {
        List<BillToPay> bills = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] headers = reader.readNext();
            Map<String, Integer> headerMap = mapHeaders(headers);

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                BillToPay bill = new BillToPay();
                bill.setDueDate(LocalDate.parse(nextLine[headerMap.get("data_vencimento")], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                bill.setPayDay(LocalDate.parse(nextLine[headerMap.get("data_pagamento")], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                bill.setValue(Double.parseDouble(nextLine[headerMap.get("valor")]));
                bill.setDescription(nextLine[headerMap.get("descricao")]);
                bill.setStatus(Boolean.parseBoolean(nextLine[headerMap.get("situacao")]));
                bills.add(bill);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new GenericException("Não foi possível salvar os dados em CSV");
		}
        return repository.saveAll(bills);
    }
	
	private Map<String, Integer> mapHeaders(String[] headers) {
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            headerMap.put(headers[i], i);
        }
        return headerMap;
    }
	
}
