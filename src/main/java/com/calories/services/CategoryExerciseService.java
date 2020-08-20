package com.calories.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.calories.exception.RessourceAlreadyExistException;
import com.calories.exception.RessourceNotFoundException;
import com.calories.model.CategoryExercise;
import com.calories.model.Exercise;
import com.calories.repository.ICategoryExerciseRepository;
import com.calories.security.services.UserPrincipale;

@Service
public class CategoryExerciseService {

	@Autowired
	ICategoryExerciseRepository categoryExerciseRepository;

	public boolean isCategoryExerciseExist(CategoryExercise category) {

		return categoryExerciseRepository.existsById(category.getId());
	}

	public List<CategoryExercise> getAllCategoryExercise() {

		return categoryExerciseRepository.findAll();
	}

	// get one category exercise by id
	public CategoryExercise getOneById(long id) throws RessourceNotFoundException {
		if (categoryExerciseRepository.existsById(id)) {
			return categoryExerciseRepository.findById(id).get();
		} else
			throw new RessourceNotFoundException(" id does not existe" + id);
	}

	// get List of category exercise name like
	public List<CategoryExercise> getListByNameLike(String name) throws Exception {
		if (name.matches("[a-zA-Z]")) {
			return categoryExerciseRepository.findByNameContaining(name);
		} else
			throw new Exception(" key word not valid");
	}

	
	public CategoryExercise addCategoryExercise(CategoryExercise category) throws RessourceAlreadyExistException {
         
		return categoryExerciseRepository.save(category);
         }

	@Transactional
	@Modifying
	public CategoryExercise updateCategoryExercise(CategoryExercise old , Long id) throws RessourceNotFoundException {
		CategoryExercise update= categoryExerciseRepository.findById(id).get();
		 if (update != null) {
			  
			 update.setDescription(old.getDescription());
			 update.setImage(old.getImage());
			 update.setName(old.getName());
			  return categoryExerciseRepository.save(update);
		 }
		 else
		 throw new RessourceNotFoundException("bas id ");
	}

	public boolean deleteCategoryExercise(long id) throws RessourceNotFoundException {
		CategoryExercise category = categoryExerciseRepository.findById(id).get();
		if (category != null) {
			categoryExerciseRepository.delete(category);
			return true;
		} else
			throw new RessourceNotFoundException("Ressource with id " + id + "doeas not exist");
	}

	public List<Exercise> getListExerciseAssociateToCategory(long id) {
		CategoryExercise category = categoryExerciseRepository.findById(id).get();
		if (category != null) {
			return category.getExercisesList();
		} else
			return null;

	}

	public String getCategoryImage(long id) {

		Optional<CategoryExercise> opt = categoryExerciseRepository.findById(id);

		CategoryExercise category;
		if (opt.isPresent()) {
			category = opt.get();
			return category.getImage();
		}
		return "";
	}

	public boolean updateImageCategory(long id, String imageName) {

		Optional<CategoryExercise> opt = categoryExerciseRepository.findById(id);
		CategoryExercise category;

		if (opt.isPresent()) {
			category = opt.get();
			category.setImage(imageName);
			categoryExerciseRepository.save(category);
			return true;
		} else
			return false;
	}
}
