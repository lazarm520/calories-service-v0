package com.calories.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.calories.exception.RessourceAlreadyExistException;
import com.calories.exception.RessourceNotFoundException;
import com.calories.model.CategoryExercise;
import com.calories.model.Exercise;
import com.calories.services.CategoryExerciseService;
import com.calories.services.ExerciseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/service")
public class CategoryExerciseController {

	
	@Autowired
	CategoryExerciseService categoryExerciseService;
	
	@Autowired
	ExerciseService exerciseService;
	
	@GetMapping("/categorieExercise/listCategoriesExercises")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<CategoryExercise> categorieExerciseData(){
		
		return categoryExerciseService.getAllCategoryExercise();
	}
	
	
	/* Image */
	@GetMapping(path="/categorieExercise/getCategoryExerciseImageName")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public Map<String,String> getCategoryExerciseImageName(@RequestParam(value="id", required=true) long id)
	{
	    return Collections.singletonMap("imgn",this.categoryExerciseService.getCategoryImage(id));

		}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path="/categorieExercise/updateCategoryExerciseImageName")
	public ResponseEntity<Boolean> updateImageName( @RequestBody ObjectNode json
			){
		long id;
		String imageName;
		try {
			id = new ObjectMapper().treeToValue(json.get("id"), Long.class);
			imageName = new ObjectMapper().treeToValue(json.get("imageName"), String.class);
			boolean test = this.categoryExerciseService.updateImageCategory(id, imageName);
			if(test)
			return new ResponseEntity<Boolean>(test,HttpStatus.OK);

		} catch (JsonProcessingException e) {
			System.out.println("Parsing Exception!!");
			e.printStackTrace();
			return new ResponseEntity<Boolean>(false,HttpStatus.NOT_ACCEPTABLE);

		}			
		return new ResponseEntity<Boolean>(false,HttpStatus.NOT_ACCEPTABLE);

			
		}
	
	/**
	 * @throws RessourceNotFoundException  **/
	
	@GetMapping("/categorieExercise/getOne/{idCat}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public CategoryExercise getCategory(@PathVariable ("idCat") long id) throws RessourceNotFoundException {
	 
		return categoryExerciseService.getOneById(id);
	}
	
	
	@GetMapping("/categorieExercise/filterExerciseToCategory")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Exercise> getExerciseAssociateToCategory(){
		return null;
	}
	
	@PostMapping("/categorieExercise/addCaetgoryExercise")
	@PreAuthorize("hasRole('ADMIN')")
	public CategoryExercise addCategoryExercise(@RequestBody CategoryExercise category ) throws RessourceAlreadyExistException {
		return categoryExerciseService.addCategoryExercise(category);
		
	}
	
	@PutMapping("/categorieExercise/updateCategoryExercise/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public CategoryExercise updateCategoryExercise(@RequestBody CategoryExercise category , @PathVariable Long id) throws RessourceNotFoundException {
		
		return categoryExerciseService.updateCategoryExercise(category , id);
		    
	}
	
	@DeleteMapping("/categorieExercise/deleteCategoryExercise/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteCategoryExercise (@PathVariable Long id ) throws RessourceNotFoundException {
		 CategoryExercise category = categoryExerciseService.getOneById(id);
		 if ( category != null) {
			 categoryExerciseService.deleteCategoryExercise(id);
			 return true;
		 }
		 
		 else
			 return false;
		 
	}

}
