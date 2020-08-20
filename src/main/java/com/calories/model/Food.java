package com.calories.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "food", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties({ "foodEaten" })
public class Food implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@Column( name="image")
	private String image;
	
	@Positive
	private double calories;

 

	@ManyToOne(fetch = FetchType.LAZY, optional = false , cascade = CascadeType.ALL)
	@JoinColumn(name = "categoriesfood_id", nullable = false)
	@JsonIgnoreProperties("foodList")
	private CategoryFood category;

	@OneToOne( cascade = CascadeType.ALL)
	@JsonIgnoreProperties
	private NutritionDetails detailfood;

	@OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST , mappedBy = "food")
	Collection<FoodEaten> foodEaten ;
	
	public Food ( String name , CategoryFood category,FoodEaten ...foodEatens ) {
		this.name = name ;
		this.category = category;
		for ( FoodEaten eaten : foodEatens ) {
			eaten.setFood(this);
		}
		this.foodEaten = Stream.of(foodEatens).collect(Collectors.toSet());
	}
}
