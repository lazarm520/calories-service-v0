package com.calories.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.calories.exception.RessourceNotFoundException;
import com.calories.model.CategoryExercise;
import com.calories.model.CategoryFood;
import com.calories.model.Food;
import com.calories.repository.ICategoryFoodRepository;

@Service
public class CategoryFoodService {

	@Autowired
	ICategoryFoodRepository categoryFoodRepository;

	public boolean isCategoryFoodExist( CategoryFood category ) {
		return categoryFoodRepository.existsById(category.getId());
	}
	
	
	public List<CategoryFood> getListCategoryFood() {
		return categoryFoodRepository.findAll();
	}

	public List<CategoryFood> getListWithNameContaining(String name) {

		return categoryFoodRepository.findAllByNameContainingIgnoreCase(name);
	}

	public CategoryFood getOneById(long id) throws RessourceNotFoundException{
		if ( categoryFoodRepository.existsById(id)) {
			return categoryFoodRepository.findById(id).get();
		}
		else
			throw new RessourceNotFoundException("bad ID asking");
		
	}

	public CategoryFood addCategoryFood(CategoryFood category){
		
		return categoryFoodRepository.save(category);
	}

	@Transactional
	@Modifying
	public CategoryFood updateCategoryFood( CategoryFood old , Long id) throws RessourceNotFoundException {
		CategoryFood update = categoryFoodRepository.findById(id).get();
		if ( update != null) {
			update.setDescription(old.getDescription());
			update.setImage(old.getImage());
			update.setName(old.getName());
			return categoryFoodRepository.save(update);
		}
		else
			throw new RessourceNotFoundException("bad Id ");
	}
	
	
	public boolean deleteCategoryFood(long id) throws RessourceNotFoundException {
		CategoryFood category = categoryFoodRepository.findById(id).get();
		if (category != null) {
			categoryFoodRepository.delete(category);
			return true;
		} else
			throw new RessourceNotFoundException("this id " + id + " doesnot exist");
	}

	List<Food> getListFoodAssociateToCategory( long id){
		CategoryFood category = categoryFoodRepository.findById(id).get();
		
		if ( category!= null) {
			return category.getFoodList();
		}
		else 
			return null;
	}
	
	
public String getCategoryImage (long id) {
		
		Optional<CategoryFood> opt = categoryFoodRepository.findById(id);
		
		CategoryFood category ;
		if ( opt.isPresent()) {
			category = opt.get();
			return category.getImage();
		}
		return "";
	}


public boolean updateImageCategory( long id , String imageName) {
	
	Optional<CategoryFood> opt = categoryFoodRepository.findById(id);
	CategoryFood category ;
	
	if ( opt.isPresent()) {
		category = opt.get();
		category.setImage(imageName);
		categoryFoodRepository.save(category);
		return true;
	}
	else
		return false;
}
	
}
