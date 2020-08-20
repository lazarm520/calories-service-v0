package com.calories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calories.model.FoodEaten;

import java.util.Date;
import java.util.List;

@Repository
public interface IFoodEatenRepository extends JpaRepository<FoodEaten, Long>{


    List<FoodEaten> findByFitnessId(Long id);

    List<FoodEaten> findByFitnessIdAndDate(Long id , Date today);

    List<FoodEaten> findByFitnessIdAndDateBetween( Long id , Date from , Date to );

    int deleteByFitnessIdAndDate( Long id , Date today);
}
