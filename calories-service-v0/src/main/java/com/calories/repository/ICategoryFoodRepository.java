package com.calories.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calories.model.CategoryFood;

@Repository
public interface ICategoryFoodRepository extends JpaRepository<CategoryFood, Long>{

	List<CategoryFood> findAllByNameContainingIgnoreCase ( String name);
}
