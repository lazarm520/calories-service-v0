package com.calories.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.calories.exception.RessourceAlreadyExistException;
import com.calories.exception.RessourceNotFoundException;
import com.calories.model.Exercise;
import com.calories.model.FitnessUser;
import com.calories.model.User;
import com.calories.repository.IExerciseRepository;
import com.calories.repository.IFitnessUserRepository;
import com.calories.repository.UserRepository;

@Service
public class FitnessUserService {

	@Autowired
	IFitnessUserRepository fitnessRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	IExerciseRepository exerciseRepository; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public FitnessUser addDetailsProfileToUser(long idUser, FitnessUser fitness) {

		User user = userRepository.findById(idUser).get();

		fitness.setUser(fitness.getUser());
		user.addFitnessDetails(fitness);

		return fitnessRepository.save(fitness);

	}
 

	public FitnessUser getCurrentUserConnect( Long id ) {
		FitnessUser fitness =  fitnessRepository.findById(id).get();
		return fitness;
	}
	
   public FitnessUser getFitnessByUserId(Long idU) {
	   FitnessUser fitness = fitnessRepository.findByUser_Id(idU);
	   return fitness;
   }
	 
}
