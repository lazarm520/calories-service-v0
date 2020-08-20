package com.calories.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calories.model.ExercisePerformed;
import com.calories.model.FitnessUser;

@Repository
public interface IFitnessUserRepository  extends JpaRepository<FitnessUser, Long>{

	FitnessUser findByUser_Id( Long id);
}
