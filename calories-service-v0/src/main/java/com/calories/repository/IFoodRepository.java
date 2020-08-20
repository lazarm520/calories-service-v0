package com.calories.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calories.model.Food;

@Repository
public interface IFoodRepository extends JpaRepository< Food, Long>{

	List<Food> findAllByCaloriesBetween( double from , double to );
	
	List<Food> findAllByNameContainingIgnoreCase( String name);
	
	String SELECT_CLAUSE ="select * from food as f";
	String COUNT_QUERY="SELECT (*) FROM food as f";
	//String ORDER_CLAUSE= "order by f.name ASC";
	@Query( value =  SELECT_CLAUSE+ 
			" inner join categoriesfood as cf on cf.id = f.categoriesfood_id "
			+"WHERE cf.name "+
			" = :category",
			nativeQuery = true ,
			countQuery = COUNT_QUERY )
	List<Food> findAllByCategory(  @Param("category") String category); 
}
