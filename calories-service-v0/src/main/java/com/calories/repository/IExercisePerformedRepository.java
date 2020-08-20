package com.calories.repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.calories.model.ExercisePerformed;

@Repository
public interface IExercisePerformedRepository extends JpaRepository<ExercisePerformed, Long>{

 

	List<ExercisePerformed> findByFitnessIdAndDateBetween(Long id , Date from , Date to );
	List<ExercisePerformed> findByFitnessIdAndDate(Long id , Date today);
	List<ExercisePerformed> findByFitnessId(Long id);
	
	@Modifying
	@Transactional
	int deleteByFitnessIdAndDate( Long id , Date today);
	
	@Modifying
	@Transactional
	int deleteByFitnessIdAndDateAndAndExerciseId( Long idF , Date today , Long idE);
	ExercisePerformed findByExerciseIdAndDate(Long idE, Date today);
	//ExercisePerformed findByFitnessIdAndDateAndExerciseId(Long idE);
	
}
