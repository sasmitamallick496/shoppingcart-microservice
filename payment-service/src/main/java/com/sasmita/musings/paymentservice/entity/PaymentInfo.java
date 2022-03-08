package com.sasmita.musings.paymentservice.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_info")
public class PaymentInfo {

	@Id
	@GeneratedValue
	@JsonIgnore
	@Column(name = "id")
	private Long id;

	@Column(name = "order_id")
	private UUID orderId;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "status")
	private String status;

}
