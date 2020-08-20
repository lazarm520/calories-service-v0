package com.calories.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calories.model.CategoryExercise;

@Repository
public interface ICategoryExerciseRepository extends JpaRepository<CategoryExercise, Long>{

	//List<CategoryExercise> findAllByNameContainingIgnoreCase ( String name);
	List<CategoryExercise> findByNameContaining(String name);
	 
	
}
