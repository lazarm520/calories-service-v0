package com.calories.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fitness" , schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({ "exercisesPerformed" })
public class FitnessUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;

	private String lastname;

	private String firstname;

	@OneToOne( cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	private User user;

	 
	private String gender;

	 
	private Date birthdate;

	 
	private double heightCm;

	 
	private double currentWeight;
	
	@Column( name="image")
	private Byte[] image;
	
	@OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST , mappedBy = "fitness"  )
	List<ExercisePerformed> exercisesPerformed;
 
	@OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST , mappedBy = "fitness"  )
	List<FoodEaten> foodEatens;
 
	@OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST , mappedBy = "fitness"  )
    List<ReportData> report ;
 
}
