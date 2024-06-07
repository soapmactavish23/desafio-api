package com.example.api.domain.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.api.domain.model.BillToPay;

public interface BillToPayRepository extends JpaRepository<BillToPay, Integer> {

	Page<BillToPay> findByStatusAndDueDateAndDescriptionContaining(Boolean status, LocalDate dueLocalDate, String description, Pageable pageable);
	
	@Query("SELECT SUM(b.value) FROM BillToPay b WHERE b.dueDate BETWEEN :startDate AND :endDate")
	Double countByPeriod(LocalDate startDate, LocalDate endDate);
	
}	
