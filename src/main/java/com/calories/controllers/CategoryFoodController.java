package com.calories.controllers;

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

import com.calories.exception.RessourceNotFoundException;
import com.calories.model.CategoryFood;
import com.calories.services.CategoryFoodService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/service")
public class CategoryFoodController {

	@Autowired
	CategoryFoodService categoryFoodService;
	
	@GetMapping("/categoriesFood/listCategoriesFood")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<CategoryFood> categorieFoodData(){
		return categoryFoodService.getListCategoryFood();
	}
	
	
	/* Image */
	@GetMapping(path="/getCategoryFoodImageName")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public Map<String,String> getCategoryFoodImageName(@RequestParam(value="id", required=true) long id)
	{
	    return Collections.singletonMap("imgn",this.categoryFoodService.getCategoryImage(id));

		}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(path="/updateCategoryFoodImageName")
	public ResponseEntity<Boolean> updateImageName( @RequestBody ObjectNode json
			){
		long id;
		String imageName;
		try {
			id = new ObjectMapper().treeToValue(json.get("id"), Long.class);
			imageName = new ObjectMapper().treeToValue(json.get("imageName"), String.class);
			boolean test = this.categoryFoodService.updateImageCategory(id, imageName);
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
	
	@GetMapping("/categoryFood/getOneById/{id}")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public CategoryFood getCategory( @PathVariable ("id") long id) throws RessourceNotFoundException {
		return categoryFoodService.getOneById(id);
	}
	
	@PostMapping("/catgeorieFood/addCategoryFood")
	@PreAuthorize("hasRole('ADMIN')")
	public CategoryFood addCategoryFood( @RequestBody CategoryFood category) {
		return categoryFoodService.addCategoryFood(category);
	}
	
	@PutMapping("/catgeorieFood/updateCategoryFood/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public CategoryFood updateCategoryExercise(@RequestBody CategoryFood category , @PathVariable Long id) throws RessourceNotFoundException {
		return categoryFoodService.updateCategoryFood(category,id);
	}
	
	@DeleteMapping("/catgeorieFood/deleteCategoryFood")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteCategoryFood( long id) throws RessourceNotFoundException {
		CategoryFood category = categoryFoodService.getOneById(id);
		if ( category !=null) {
			categoryFoodService.deleteCategoryFood(category.getId());
			return true;
		}
		else 
			return false;
	}
	
}
