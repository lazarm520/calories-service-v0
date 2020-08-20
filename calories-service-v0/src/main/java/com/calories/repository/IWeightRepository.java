package com.calories.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calories.model.Weight;

@Repository
public interface IWeightRepository extends JpaRepository<Weight, Long>{

	List<Weight> findByFitnessId( Long id);
}