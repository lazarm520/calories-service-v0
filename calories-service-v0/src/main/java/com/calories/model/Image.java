package com.calories.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id ; 
	
	private String path;
	
	private String data;
	
	private String directory ;
	
	
	@OneToOne( fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private FitnessUser fitnessImage;
}
