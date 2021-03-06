package com.pirate.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "purchasehistory")
@Data
public class PurchaseHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6123484808819068481L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String userid;

	private String itemName;

	private String brand;

	private double rating;
	
	private String type;
}