package com.calories.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.calories.exception.RessourceNotFoundException;
import com.calories.model.CategoryFood;
import com.calories.model.Food;
import com.calories.model.NutritionDetails;
import com.calories.repository.ICategoryFoodRepository;
import com.calories.repository.IFoodRepository;

@Service
public class FoodService {

	@Autowired
	IFoodRepository foodRepository;
	
	@Autowired
	ICategoryFoodRepository categoryFoodRepository;
	
	@Autowired
	FileImageService fileService;
	
	public List<Food> getAllFood(){
		return foodRepository.findAll();
	}
	
	public List<Food> searchFood ( String name){
		return foodRepository.findAllByNameContainingIgnoreCase(name);
	}
	
	
	public Food addFood( Food food , Long idCat) throws RessourceNotFoundException {
		 Food f = new Food();
		 CategoryFood cat = categoryFoodRepository.findById(idCat).get();
		 
		 f.setCalories(food.getCalories());
		 f.setImage(food.getImage());
		 f.setName(food.getName());
		 // detail to add check
		 f.setDetailfood(food.getDetailfood());
		 
		 f.setCategory(food.getCategory());
		 cat.addFood(f);
		 
		  return foodRepository.save(f);
		 
		  
	}
	
	@Transactional
	@Modifying
	public Food updateFood(  Food old , Long id) throws RessourceNotFoundException {
		
		Food update = foodRepository.findById(id).get();
		if ( update != null) {
			update.setCalories(old.getCalories());
			update.setImage(old.getImage());
			update.setName(old.getName());
			update.setCategory(update.getCategory());
			update.setDetailfood(old.getDetailfood());
			
			return foodRepository.save(update);
		}
		else
			throw new RessourceNotFoundException("bad ID");
	}
	
	public boolean deleteFood( Long id) throws RessourceNotFoundException {
		Food toDelete = foodRepository.findById(id).get();
		
		if (toDelete != null) {
			foodRepository.delete(toDelete);
			return true;
		}
		else 
			throw new RessourceNotFoundException("wrong ID ");
			 
	}
	
	public Long countFood() {
		return foodRepository.count();
	}
	
	public List<Food> getFoodByCategoryType(String category){
		
		List<Food> foods =  foodRepository.findAllByCategory(category);
		return foods;
	}
	
	public List<Food> getfoodCaloriesBetween( double from , double to){
		return foodRepository.findAllByCaloriesBetween(from, to);
	}

	public Food getOneById(Long id) {
		// TODO Auto-generated method stub
		return foodRepository.findById(id).get();
	}
	

	public NutritionDetails getFoodDetails (Long id) {
		Food food = foodRepository.findById(id).get();
		return food.getDetailfood();
	}
	
}
