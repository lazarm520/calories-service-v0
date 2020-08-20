package com.calories.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.calories.exception.RessourceNotFoundException;
import com.calories.model.CategoryExercise;
import com.calories.model.Exercise;
import com.calories.repository.ICategoryExerciseRepository;
import com.calories.repository.IExerciseRepository;

@Service
public class ExerciseService {

	@Autowired
	IExerciseRepository exerciseRepository;
	
	@Autowired
	ICategoryExerciseRepository categoryExerciseRepository ;
	
	@Autowired
	FileImageService fileService;
	
	public List<Exercise> getAllExercise(){
		return exerciseRepository.findAll();
	}
	
	public List<Exercise> searchExercise( String name){
		return exerciseRepository.findAllByNameContainingIgnoreCase(name);
	}
	
	// multiple search item
	public List<Exercise> searchMultiple(String name , String category , double from , double to ){
		
		List<Exercise> results = new ArrayList<Exercise>();
		List<Exercise> resultsWithCategory = new ArrayList<Exercise>();
		if ( category != null) {
			results = exerciseRepository.findAllByCategory(category.toLowerCase());
			for ( Exercise e : results) {
				if (( e.getCaloriesBurned()>= from || e.getCaloriesBurned() <= to) &&(e.getName().contains(name)) 
						|| 
				   (( e.getCaloriesBurned()>= from || e.getCaloriesBurned() <= to) && (e.getName() == ""))) {
					resultsWithCategory.add(e);
				}
			}
			return resultsWithCategory ;
		}
		
		if ( category == null ) {
			results= exerciseRepository.findByCaloriesBurnedBetweenAndNameContainingIgnoreCase(from, to ,name);
			return results;
		}

		return results;
 
	}
	
	public Exercise addExercise( Exercise exercise , Long idCat ) {
		Exercise eToAdd = new Exercise();
		CategoryExercise cat = categoryExerciseRepository.findById(idCat).get();
		
		eToAdd.setCaloriesBurned(exercise.getCaloriesBurned());
		eToAdd.setDuration(exercise.getDuration());
		eToAdd.setImage(exercise.getImage());
		eToAdd.setName(exercise.getName());
		
		eToAdd.setCategory(exercise.getCategory());
		cat.addExercise(eToAdd);

		this.exerciseRepository.save(eToAdd);
		return eToAdd;
	}
	 
	@Transactional
	@Modifying
	public Exercise updateExercise( Exercise old , Long id ) throws RessourceNotFoundException {

		Exercise update = exerciseRepository.findById(id).get();
		if ( update != null) {
			update.setName(old.getName());
			update.setImage(old.getImage());
			update.setCaloriesBurned(old.getCaloriesBurned());
			update.setDuration(old.getDuration());
			update.setCategory(update.getCategory());
			return exerciseRepository.save(update);
		}
		else 
			throw new RessourceNotFoundException("id not exist" + old.getId());
	}
	
	public boolean deleteExercise( Long id) throws RessourceNotFoundException {
		Exercise toDelete = exerciseRepository.findById(id).get();
		if ( toDelete != null) {
			exerciseRepository.delete(toDelete);
			return true ;
		}
		else
			throw new RessourceNotFoundException("bas id ");
	}
	
	public Long countExercise() {
		return exerciseRepository.count();
	}
	
	public List<Exercise> getExerciseByCategoryType ( String category){
		
		List<Exercise> exercises = exerciseRepository.findAllByCategory(category);
		return exercises ;
	}
	
	
	public List<Exercise> getExerciseCaloriesBetween( double from , double to){
		return exerciseRepository.findByCaloriesBurnedBetween(from, to);
	}
	
	public List<Exercise> getExerciseMinutesBetween( int from , int to ){
		return exerciseRepository.findByDurationBetween(from, to);
	}
	
	public Exercise getOneById(long id) {
		return exerciseRepository.findById(id).get();
	}
	
}
