package com.calories.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.calories.model.Food;
import com.calories.model.NutritionDetails;
import com.calories.services.CategoryFoodService;
import com.calories.services.FoodService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/service")
public class FoodController {

	@Autowired
	CategoryFoodService categoryFoodService;
	
	@Autowired
	FoodService foodService;
	
	
	// getAll food 
	@GetMapping("/food/listeFood")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Food> getAllFoodDate(){
		List<Food> result = foodService.getAllFood();
		return  (result != null) ? result : new ArrayList<Food>();
	}
	
	// get food calories between
	@GetMapping("/food/listeFoodCaloriesBetween")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Food> getFoodCaloriesBetween(@RequestParam(defaultValue = "0", value = "from") double from,
			@RequestParam(value = "to" , defaultValue = "1500") double to){
		List<Food> getfoodCaloriesBetween = foodService.getfoodCaloriesBetween(from, to);
		return getfoodCaloriesBetween !=null ? getfoodCaloriesBetween : new ArrayList<Food>();
	}
	
	// search by Category 
	@GetMapping("/food/listeFoodByCategory")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Food> getFoodByCategoryAssociate(@RequestParam ("category") String category){
		
		List<Food> foodByCategoryType = foodService.getFoodByCategoryType(category);
		return foodByCategoryType != null ? foodByCategoryType : new ArrayList<Food>() ;
	}
	
	// search by keyword
	@GetMapping("/food/listeFoodByKeyWord")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public List<Food> getFoodByKeyword( @RequestParam(value = "keyword") String keyword){
		List<Food> searchFood = foodService.searchFood(keyword);
		return searchFood!=null ? searchFood : new ArrayList<Food>();
	}
	
	// add Food 
	@PostMapping("/category/{idCat}/addFood")
	@PreAuthorize("hasRole('ADMIN')")
	public Food addFood(@RequestBody Food food , @PathVariable ("idCat") Long id) throws RessourceNotFoundException {
		Food addfood = foodService.addFood(food, id);
		return addfood;
	}
	
	// update Food
	@PutMapping("/food/updateFood/{idFd}")
	@PreAuthorize("hasRole('ADMIN')")
	public Food updateFood(@PathVariable("idFd") Long id , @RequestBody Food food ) throws RessourceNotFoundException {
		if ( (foodService.getOneById( id)) != null) {
			return foodService.updateFood(food, id);
		}
		
		else
			throw new RessourceNotFoundException("id not found" + id);
	}
	
	// delete food
	@DeleteMapping("/food/deleteFood/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteFood ( @PathVariable Long id) throws RessourceNotFoundException {
		return foodService.deleteFood(id);
	}
	
	
	// count food 
	@GetMapping("/food/countFood")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public long countFood() {
		Long countFood = foodService.countFood();
		return countFood != 0 ? countFood : 0;
	}
	
	@GetMapping("/food/{idF}/getDetailsFood")
	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	public NutritionDetails getFoodDetails(@PathVariable("idF") Long id) {
		Food foodSelected = foodService.getOneById(id);
		return foodSelected.getDetailfood();
		
	}
}
