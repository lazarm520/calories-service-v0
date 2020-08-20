package com.calories.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.calories.config.request.SignupRequest;
import com.calories.exception.RessourceNotFoundException;
import com.calories.model.FitnessUser;
import com.calories.model.User;
import com.calories.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getCurrent(long id) {
		return userRepository.findById(id).get();
	}

	public User updatePassword(Long id, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub

		User connected = userRepository.findById(id).get();

		if (passwordEncoder.matches(oldPassword, connected.getPassword())) {
			connected.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(connected);
		}
		return connected;
	}

	public User updateUsername(Long id, String oldUsername, String newUsername) {

		User current = userRepository.findById(id).get();

		if (current != null) {
			current.setUsername(newUsername);
			// this.fitnessRepository.save(current);
			this.userRepository.save(current);
			return current;
		}

		return current;

	}

	public User getDetailsUserByFitnessId(Long id) {
		User fitness = userRepository.findByFitnessId(id);
		return fitness;
	}

	public User updateRequestSign(Long id , SignupRequest request) {
		
		User connected = userRepository.findById(id).get();
		
		if (! passwordEncoder.matches(request.getPassword(), connected.getPassword()) ) {
			connected.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		
		if (connected.getEmail().equals(request.getEmail()) == false ) {
			connected.setEmail( request.getEmail());
		}
		
		if (connected.getUsername().equals(request.getUsername() )== false ) {
			connected.setUsername( request.getUsername());
		}
		 
		this.userRepository.save(connected);
		
		return connected;
	}

	public boolean hasProfileData(Long id) {
		// TODO Auto-generated method stub
		User connected = userRepository.findById(id).get();
		if ( connected !=null && connected.isHasFitnessData() == false )
		return false;
		else
			return true ;
	}
}
