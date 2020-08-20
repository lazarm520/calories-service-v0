package com.calories.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "foodeaten", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({ "fitness" })
public final class FoodEaten  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "food_id",referencedColumnName = "id", nullable = false)
	private Food food;

	@Column(name = "date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;
 
	@ManyToOne(fetch = FetchType.LAZY, optional = false , cascade = CascadeType.DETACH)
    @JoinColumn(name = "fitness_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FitnessUser fitness;

	/**
	 * @param food
	 * @param date
	 * @param fitness
	 */
	public FoodEaten(Food food, Date date, FitnessUser fitness) {
		super();
		this.food = food;
		this.date = date;
		this.fitness = fitness;
	}
	
	public void setFood( Food food) {
		if ( this.food != null) {
			//this.food.
		}
		this.food=food;
		this.food.getFoodEaten().add(this);
	}

}
