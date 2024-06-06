package com.example.api.domain.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "contas_a_pagar")
public class BillToPay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(name = "data_vencimento")
	private Date dueDate;
	
	@NotNull
	@Column(name = "data_pagamento")
	private Date payDay;
	
	@NotNull
	@Column(name = "valor")
	private Double value;
	
	@NotBlank
	@Column(name = "descricao")
	private String description;
	
	@NotNull
	@Column(name = "situacao")
	private Boolean status;
}
