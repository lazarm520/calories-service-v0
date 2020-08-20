package com.calories.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

@Entity
@Table(name = "nutrition", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "food"  })
public class NutritionDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
	//@UniqueElements
	@NotNull
	private String name ;
	
	private double calories;
	private double eau;
	private double proteins;
	private double sucres;
	private double fibres;
	private double acidessatures;
	private double cholesterol;
	private double calcium;
	private double potasium;
	private double sodium;
	private double VitamineD;
	private double VitamineE;
	private double VitamineC;
	 
	private double VitamineB3;
	private double VitamineB6;
	private double VitamineB9;
	private double VitamineB12;
	private double VitamineB1;

	@OneToOne(mappedBy = "detailfood" , cascade = CascadeType.ALL)
	private Food food;
	
}
