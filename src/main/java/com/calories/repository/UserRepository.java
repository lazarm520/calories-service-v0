package com.calories.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.calories.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	//@Query( value ="SELECT * from users as u join fitness as f on f.user_id = u.id WHERE f.id = :id" , nativeQuery = true)
	User findByFitnessId( Long id);
	
	


}
