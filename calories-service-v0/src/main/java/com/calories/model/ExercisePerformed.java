package com.calories.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exerciseperformed", schema = "public")
@Getter
@Setter
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@JsonIgnoreProperties({ "fitness"  })
public class ExercisePerformed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;	

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private FitnessUser fitness; // done by user

	@ManyToOne(cascade = CascadeType.PERSIST , fetch = FetchType.LAZY, optional = false)
	private Exercise exercise; // exercise Done

	@Column(name = "DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;

	/**
	 * @param user
	 * @param exercise
	 * @param today
	 */
	public ExercisePerformed(FitnessUser fitness, Exercise exercise, Date today) {
		super();
		this.fitness = fitness;
		this.exercise = exercise;
		this.date = today;

	}

	public void setExercise(Exercise exercise) {
		if (this.exercise != null) {
			this.exercise.getExercisesPerformed().remove(this);
		}
		this.exercise = exercise;
		this.exercise.getExercisesPerformed().add(this);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ExercisePerformed) {
			ExercisePerformed otherId = (ExercisePerformed) object;
			return (otherId.exercise.getId() == this.exercise.getId()) || (otherId.fitness.getId() == this.fitness.getId() + 1 );
		}
		return false;
	}
}
