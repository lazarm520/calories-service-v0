package com.calories.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calories.model.FitnessUser;
import com.calories.model.User;
import com.calories.model.Weight;
import com.calories.repository.IFitnessUserRepository;
import com.calories.repository.IWeightRepository;
import com.calories.repository.UserRepository;

@Service
public class WeightService {

	
	@Autowired
	IFitnessUserRepository fitnessRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IWeightRepository weightRepository;
	
	
	// add or update weight to current User
	public User addOrUpdateWeightToCurrentUser( Long id , Double newWeight) {
		
		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		
		User user = userRepository.findById(id).get();
		FitnessUser fitness = user.getFitness();
		
		Weight weight = new Weight();
		weight.setDate(today);
		weight.setPoids(newWeight);
		weight.setFitness(fitness);
		
		fitness.setCurrentWeight(newWeight);
		fitnessRepository.save(fitness);
		weightRepository.save(weight);
		userRepository.save(user);
		
		return user;
	}
	
	// get List weight for current User
	public List<Weight> getDataWeightForCurrentUser( Long idU){
		
		User user = userRepository.findById(idU).get();
		FitnessUser fitness = user.getFitness();
		
		List<Weight> listWeight = weightRepository.findByFitnessId(fitness.getId());
		return listWeight;
	}
}
