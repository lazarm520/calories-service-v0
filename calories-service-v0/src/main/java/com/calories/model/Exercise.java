package com.calories.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.servlet.annotation.HandlesTypes;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exercise", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "exercisesPerformed" })
public class Exercise implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", columnDefinition = "TEXT")
	@NotNull
	private String name;

	@Column( name="image")
	private String image;

	@Column(name = "duree")
	private int duration;

	@Column(name = "caloriesBurned")
	private double caloriesBurned;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "categoryexericse_id", referencedColumnName = "id")
	@JsonIgnoreProperties("exercisesList")
	private CategoryExercise category;
	
	@OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST , mappedBy = "exercise")
	Collection<ExercisePerformed> exercisesPerformed ;
	
	
	//book reference 
	public Exercise ( String name , CategoryExercise category,ExercisePerformed ...exercisePerformeds ) {
		this.name = name ;
		this.category = category;
		for ( ExercisePerformed performed : exercisePerformeds ) {
			performed.setExercise(this);
		}
		this.exercisesPerformed = Stream.of(exercisePerformeds).collect(Collectors.toSet());
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if ( this == obj) {
			return true;
		}
		if ( ! (obj instanceof Exercise) ) {
				return false;}
		return id != null && id.equals(((Exercise) obj).id);
	}

	public Exercise(String name, String image, int duration, double caloriesBurned, CategoryExercise category) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.image = image;
		this.duration = duration;
		this.category = category;
	}
	
 

}
