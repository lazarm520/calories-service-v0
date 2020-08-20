package com.calories.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

@Entity
@Table(name = "categoryexercise", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter @Setter
@JsonIgnoreProperties({ "exercisesList" })
public class CategoryExercise  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	@NotNull
	private String name;

	@OneToMany( fetch = FetchType.LAZY, mappedBy = "category" ,cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(name="categroy")
	@OrderBy("name ASC")
	private List<Exercise> exercisesList;
	
	@Column(name = "description",columnDefinition="TEXT")
	private String description;
	
	@Column( name="image")
	private String image;

 
	public void addExercise( Exercise exercise) {
		exercisesList.add(exercise);
		exercise.setCategory(this);
	}
	
	public void removeExerciseFromCategory(Exercise exercise) {
		exercisesList.remove(exercise);
		exercise.setCategory(null);
	}
	
}
