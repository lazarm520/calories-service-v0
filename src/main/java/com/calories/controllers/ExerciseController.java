 package com.calories.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.calories.exception.RessourceNotFoundException;
import com.calories.model.CategoryExercise;
import com.calories.model.Exercise;
import com.calories.services.CategoryExerciseService;
import com.calories.services.ExerciseService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/service")
public class ExerciseController {

	@Autowired
	ExerciseService exerciseService;

	@Autowired
	CategoryExerciseService categoryExerciseService;

	// get all
	@GetMapping("/exercise/listeExercises")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Exercise> getAllExercisesData() {
		return exerciseService.getAllExercise();
	}

	
	// get all calories burned between
	@GetMapping("/exercise/listeExercisesByCalories")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Exercise> getExerciseCaloriesBurnedBetween(@RequestParam(value = "from") double from,
			@RequestParam(value = "to") double to) {

		List<Exercise> exerciseCaloriesBetween = exerciseService.getExerciseCaloriesBetween(from, to);
		return exerciseCaloriesBetween !=null ?exerciseCaloriesBetween : new ArrayList<Exercise>();

	}

	// get all minutes between
	@GetMapping("/exercise/listeExercisesByMinutes")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Exercise> getExerciseMinutesBetween(@RequestParam(value = "from", defaultValue = "0") int from,
			@RequestParam(value = "to", defaultValue = "120") int to) {

		return exerciseService.getExerciseMinutesBetween(from, to);

	}

	// search by category // add ignore case
	@GetMapping("/exercise/listeExercisesByCategory")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Exercise> getExerciseByCategory(@RequestParam(value = "category") String category) {

		List<Exercise> exerciseByCategoryType = exerciseService.getExerciseByCategoryType(category);
		return exerciseByCategoryType != null ? exerciseByCategoryType : new ArrayList<Exercise>();
	}
	// search by keyword
	@GetMapping("/exercise/listeExercisesByKeyWord")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Exercise> getExerciseByKeyWord(@RequestParam(value = "keyword") String keyword ) {

		List<Exercise> searchExercise = exerciseService.searchExercise(keyword);
		return searchExercise != null ? searchExercise : new ArrayList<Exercise>();
	}

	// search Exercise by name contain + catgorie + calories between 
	@GetMapping("/exercise/listeExercisesMultipleCritere")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Exercise> getExerciseMultipleCritere(
			@RequestParam(value = "category" , required=false) String category, 
			@RequestParam(value = "keyword" , required=false) String keyword ,
			@RequestParam(value = "from", defaultValue = "0" , required=false) int from,
			@RequestParam(value = "to", defaultValue = "1000" , required=false) int to) {
		
		List<Exercise> searchExercise = exerciseService.searchMultiple(keyword, category, from, to);
		return searchExercise;
	}
	// add Exercise OK
	@PostMapping("/category/{idCat}/addExercise")
	@PreAuthorize("hasRole('ADMIN')")
	public Exercise addExercise(  @RequestBody Exercise exercise ,@PathVariable("idCat") Long id ) throws RessourceNotFoundException {
	    
		Exercise addExercise = exerciseService.addExercise(exercise , id);
		return  addExercise;
		
	}
	// update Exercise OK

	@PutMapping("/exercise/updateExercise/{idEx}")
	@PreAuthorize("hasRole('ADMIN')")
	public Exercise updateExercise ( @PathVariable("idEx") Long idEx , @RequestBody Exercise exercise) throws RessourceNotFoundException{
		if ((exerciseService.getOneById(idEx)) != null  )	{
			return exerciseService.updateExercise(exercise , idEx );
		}
		else 
			throw new RessourceNotFoundException("id not found " + idEx);
		
	}
	// delete exercise OK
	@DeleteMapping("/exercise/deleteExercise/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteExercise ( @PathVariable Long id ) throws RessourceNotFoundException {
		 
		
		return exerciseService.deleteExercise(id);
	}
	
	// count exercise 
	@GetMapping("/exercise/countExercise")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public long countExercise() {
		Long countExercise = exerciseService.countExercise();
		return countExercise != 0 ? countExercise : 0 ;
	}
	
	// getOne
	@GetMapping("/exercise/getOne/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public Exercise getOneInDetails( @PathVariable ("id") long id) {
		return exerciseService.getOneById(id);
	}
	
}
