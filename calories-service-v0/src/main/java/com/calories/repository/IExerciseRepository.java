package com.calories.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calories.model.CategoryExercise;
import com.calories.model.Exercise;

@Repository
public interface IExerciseRepository extends  JpaRepository<Exercise, Long> , JpaSpecificationExecutor<Exercise> {
	List<Exercise> findByCaloriesBurnedBetween(double from , double to );
	List<Exercise> findByDurationBetween ( int from , int to);
	List<Exercise> findAllByNameContainingIgnoreCase ( String name);
	
	List<Exercise> findAllByNameContainingIgnoreCaseAndCaloriesBurnedBetweenAndCategory(
			String name , double from , double to , String category);
	
	List<Exercise> findByCaloriesBurnedBetweenAndNameContainingIgnoreCase(
			double from , double to ,  String name);
	
	String SELECT_CLAUSE ="select * from exercise as ex";
	String WHERE_CLAUSE ="where ce.name";
	String COUNT_QUERY="SELECT (*) FROM exercise as ex";
	String ORDER_CLAUSE= " order by e.name ASC";
	
	@Query( value =  SELECT_CLAUSE+ 
			" inner join categoryexercise as ce on ce.id = ex.categoryexericse_id "
			+WHERE_CLAUSE+
			"= :category",
			nativeQuery = true ,
			countQuery = COUNT_QUERY )
	List<Exercise> findAllByCategory( @Param("category") String category);
	
	Exercise findByIdAndCategory( Long id , Long categoryId);
}
