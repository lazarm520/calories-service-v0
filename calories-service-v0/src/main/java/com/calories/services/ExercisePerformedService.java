package com.calories.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calories.model.Exercise;
import com.calories.model.ExercisePerformed;
import com.calories.model.FitnessUser;
import com.calories.model.FoodEaten;
import com.calories.model.ReportData;
import com.calories.model.User;
import com.calories.repository.IExercisePerformedRepository;
import com.calories.repository.IExerciseRepository;
import com.calories.repository.IFitnessUserRepository;
import com.calories.repository.UserRepository;

@Service
public class ExercisePerformedService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	IFitnessUserRepository fitnessRepository;

	@Autowired
	IExerciseRepository exerciseRepository;

	@Autowired
	IExercisePerformedRepository performedRepository;

	

	// add exercise to user
	public boolean addExercisePerformedToFitnessUser(Long idE, Long idU) {

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());

		User connected = userRepository.findById(idU).get();
		FitnessUser fitness = connected.getFitness();
		Exercise exercise = exerciseRepository.findById(idE).get();
		ExercisePerformed exercisePerformed = new ExercisePerformed();

		ReportData report = new ReportData();
		if (fitness != null) {
			
			exercisePerformed.setExercise(exercise);
			exercisePerformed.setFitness(fitness);
			exercisePerformed.setDate(today);

			fitness.getExercisesPerformed().add(exercisePerformed);
			performedRepository.save(exercisePerformed);
			
//			report.setFitness(fitness);
//			report.set
			fitnessRepository.save(fitness);

			return true;
		} else
			return false;
	}

	// get Exercise Done Today fot current User  //OK
	public List<ExercisePerformed > getExercisePerformedDoneTodayForCurrentUser( Long id ){
		
		User connected = userRepository.findById(id).get();
		FitnessUser fitness = connected.getFitness();
		
		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());

		
		List<ExercisePerformed> exercisesToday = performedRepository.findByFitnessIdAndDate(fitness.getId(), today);
		
		return exercisesToday;
	}
	
	// get liste exercise between date
		public List<ExercisePerformed> findPerformedPeriod(Long id, Date from, Date to) {
			
			User user = userRepository.findById(id).get();
			FitnessUser current = user.getFitness();

			List<ExercisePerformed> exercisesPerformed = new ArrayList<ExercisePerformed>();
			if (current != null) {
				exercisesPerformed = performedRepository.findByFitnessIdAndDateBetween(current.getId(), from, to);
				return exercisesPerformed != null ? exercisesPerformed : new ArrayList<ExercisePerformed>();
			}
			return exercisesPerformed;
		}
		
	
	// get all exercise without date period
	public List<ExercisePerformed> getAllExercisePerformedByFitnessUser(Long id) {
		
		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();
		List<ExercisePerformed> liste = new ArrayList<ExercisePerformed>();

		if (current != null) {
			liste = current.getExercisesPerformed();
		}

		return liste.size() > 0 ? liste : new ArrayList<ExercisePerformed>();
	}

	// remove one exercise form list , he can remove only with current date
	@Transactional
	public boolean removeExercisePerformedFromFitnessUser(Long idE , Long id) {

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());

		User user = userRepository.findById(id).get();
		FitnessUser fitness = user.getFitness();
		Exercise exeToRemove = exerciseRepository.findById(idE).get();
		List<ExercisePerformed> doneToday = performedRepository.findByFitnessIdAndDate(fitness.getId(), today);
		
		performedRepository.deleteByFitnessIdAndDateAndAndExerciseId(fitness.getId(), today, exeToRemove.getId());
		
		return true;
	}

	// remove all with only current date
	@Transactional
	public boolean removeAllExercisePerformedFromFitnessUser(Long id) {

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		
		User user = userRepository.findById(id).get();
		FitnessUser fitness = user.getFitness();
		
		boolean deleteDone = false ; 
		
		if (fitness != null) {
			 performedRepository.deleteByFitnessIdAndDate(fitness.getId(), today);
			 

			 deleteDone = true ;
				return deleteDone;
		}
		return deleteDone;
	}
	
 

	
	// get time Done today for current user
	public double countTimeDoneForCurrentUserAndToday( Long id) {
		
		User user = userRepository.findById(id).get();
		FitnessUser fitness = user.getFitness();
		
		
		List<ExercisePerformed> doneToday = new ArrayList<ExercisePerformed>();
		
		java.util.Date date=new java.util.Date();
		java.sql.Date today=new java.sql.Date(date.getTime());
		
		double timeTotal = 0 ;
		if ( fitness != null) {
			doneToday = performedRepository.findByFitnessIdAndDate(fitness.getId(), today);
			 for ( ExercisePerformed e : doneToday) {
				 timeTotal += e.getExercise().getDuration();
			 }
		}
		return timeTotal;
		
	}
	
}
