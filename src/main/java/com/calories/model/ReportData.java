package com.calories.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

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
@Table(name = "reportdata",  schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "idreportdata")
public final class ReportData  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
   
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    private FitnessUser fitness;

	@Column(name = "date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date date;

	private Double poids ;

	private Double calories ;

	private Double proteins ; 
	
	private double sel ;
	
	private Double sucres; 
	
	private Double vitamines;
	
	private Double fibres ; 
	
	private Double cholesterol ; 

	 
		
	public double totalSel( FitnessUser fitness ) {
		
		List<FoodEaten> eatens = fitness.getFoodEatens();
		for ( FoodEaten f : eatens) {
			Food referenceFood = f.getFood();
			this.sel += 	referenceFood.getDetailfood().getSodium() 
							+ referenceFood.getDetailfood().getCalcium()
							+ referenceFood.getDetailfood().getPotasium();
		 }
		return sel ;
	}
	
	public double totalProteins (FitnessUser fitness ) {
		List<FoodEaten> eatens = fitness.getFoodEatens();
		for ( FoodEaten f : eatens) {
			Food referenceFood = f.getFood();
			this.proteins += 	referenceFood.getDetailfood().getProteins();
		 }
		return proteins ;
	}
	
	public double totalVitamines (FitnessUser fitness ) {
		List<FoodEaten> eatens = fitness.getFoodEatens();
		for ( FoodEaten f : eatens) {
			Food referenceFood = f.getFood();
			this.vitamines += 	referenceFood.getDetailfood().getVitamineB1() 
						+ referenceFood.getDetailfood().getVitamineB12()
						+ referenceFood.getDetailfood().getVitamineB3()
						+ referenceFood.getDetailfood().getVitamineB6()
						+ referenceFood.getDetailfood().getVitamineB9()
						+ referenceFood.getDetailfood().getVitamineC()
						+ referenceFood.getDetailfood().getVitamineD()
						+ referenceFood.getDetailfood().getVitamineE();
		 }
		return vitamines ;
	}
	
	public double totalSucres (FitnessUser fitness ) {
		List<FoodEaten> eatens = fitness.getFoodEatens();
		for ( FoodEaten f : eatens) {
			Food referenceFood = f.getFood();
			this.sucres += 	referenceFood.getDetailfood().getSucres();
		 }
		return sucres ;
	}
	
	public double totalFibres (FitnessUser fitness ) {
		List<FoodEaten> eatens = fitness.getFoodEatens();
		for ( FoodEaten f : eatens) {
			Food referenceFood = f.getFood();
			this.cholesterol += 	referenceFood.getDetailfood().getAcidessatures()
								  + referenceFood.getDetailfood().getCholesterol();
		 }
		return cholesterol ;
	}
	
	public double totalCholesterol (FitnessUser fitness) {
		List<FoodEaten> eatens = fitness.getFoodEatens();
		for ( FoodEaten f : eatens) {
			Food referenceFood = f.getFood();
			this.fibres += 	referenceFood.getDetailfood().getFibres();
		 }
		return fibres ;
	}

	/**
	 * @param fitness
	 * @param date
	 */
	public ReportData(FitnessUser fitness, Date date) {
		super();
		this.fitness = fitness;
		this.date = date;
	}
	
	public void setFitness( FitnessUser fitness) {
		if ( this.fitness != null) {
			//this.food.
		}
		this.fitness=fitness;
		this.fitness.getReport().add(this);
	}

	
}
