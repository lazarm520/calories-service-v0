package com.calories.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calories.model.FitnessUser;
import com.calories.model.Food;
import com.calories.model.FoodEaten;
import com.calories.model.ReportData;
import com.calories.model.User;
import com.calories.repository.IFitnessUserRepository;
import com.calories.repository.IFoodEatenRepository;
import com.calories.repository.IFoodRepository;
import com.calories.repository.UserRepository;

@Service
public class FoodEatenService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	IFitnessUserRepository fitnessRespository;

	@Autowired
	IFoodRepository foodRepository;

	@Autowired
	IFoodEatenRepository eatenRepository;

	// get all foodEaten for user without period

	public List<FoodEaten> getAllFoodEatenForUser(Long id) {
		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		List<FoodEaten> eatens = eatenRepository.findByFitnessId(current.getId());
		return eatens;
	}
	// get all FoodEaten for user with date between

	public List<FoodEaten> getAllFoodEatenForUserAndDateBetween(Long id, Date from, Date to) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		List<FoodEaten> eatensBetween = eatenRepository.findByFitnessIdAndDateBetween(current.getId(), from, to);
		return eatensBetween;
	}

	// get all foodEaten for use and date today
	public List<FoodEaten> getAllFoodEatenForUserToday(Long id) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());

		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(current.getId(), today);
		return eatensToday;
	}

	// get calories consumed today
	public double getCaloriesConsumedForUserToday(Long id) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		double totalCalories = 0;
		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(current.getId(), today);
		for (FoodEaten f : eatensToday) {
			totalCalories += f.getFood().getCalories();
		}
		return totalCalories;
	}

	// get total Sel consumed today
	public double getSaltConsumedForUserToday(Long id) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		double totalSels = 0;
		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(current.getId(), today);
		for (FoodEaten f : eatensToday) {
			totalSels += f.getFood().getDetailfood().getCalcium() + f.getFood().getDetailfood().getPotasium()
					+ f.getFood().getDetailfood().getSodium();
		}
		return totalSels;
	}

	// get total Proteins consumed today
	public double getProteinsConsumedForUserToday(Long id) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		double totalProteins = 0;
		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(current.getId(), today);
		for (FoodEaten f : eatensToday) {
			totalProteins += f.getFood().getDetailfood().getProteins();
		}
		return totalProteins;
	}

	// get total Cholesterol consumed today
	public double getCholesterolConsumedForUserToday(Long id) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		double totalCholesterol = 0;
		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(current.getId(), today);
		for (FoodEaten f : eatensToday) {
			totalCholesterol += f.getFood().getDetailfood().getAcidessatures()
					+ f.getFood().getDetailfood().getCholesterol();
		}
		return totalCholesterol;
	}

	// get total Vitamines consumed today
	public double getVitaminesConsumedForUserToday(Long id) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		double totalVitamines = 0;
		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(current.getId(), today);
		for (FoodEaten f : eatensToday) {
			totalVitamines += f.getFood().getDetailfood().getVitamineB1() + f.getFood().getDetailfood().getVitamineB12()
					+ f.getFood().getDetailfood().getVitamineB3() + f.getFood().getDetailfood().getVitamineB6()
					+ f.getFood().getDetailfood().getVitamineB9() + f.getFood().getDetailfood().getVitamineC()
					+ f.getFood().getDetailfood().getVitamineD() + f.getFood().getDetailfood().getVitamineE();
		}
		return totalVitamines;
	}

	// get total Fibres consumed today
	public double getFibresConsumedForUserToday(Long id) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		double totalFibres = 0;
		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(current.getId(), today);
		for (FoodEaten f : eatensToday) {
			totalFibres += f.getFood().getDetailfood().getFibres();
		}
		return totalFibres;
	}

	// get nbre foodEaten today
	public int getNbreFoodEaten(Long id) {

		User user = userRepository.findById(id).get();
		FitnessUser current = user.getFitness();

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		int totalEatens = 0;
		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(current.getId(), today);
		totalEatens = eatensToday.size();

		return totalEatens;
	}

	// add Food to User Today
	public boolean addFoodTOCurrentUser(Long idF, Long idU) {

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());

		User connected = userRepository.findById(idU).get();
		FitnessUser fitness = connected.getFitness();

		Food food = foodRepository.findById(idF).get();
		FoodEaten eaten = new FoodEaten();

		ReportData report = new ReportData();
		if (fitness != null) {
			eaten.setFitness(fitness);
			eaten.setFood(food);
			eaten.setDate(today);

			fitness.getFoodEatens().add(eaten);

			eatenRepository.save(eaten);
			fitnessRespository.save(fitness);

			return true;
		}

		return false;
	}

	// remove one food from current user
	public boolean removeEatenFoodFromListForFitnessUser(Long idF, Long idU) {
		// TODO Auto-generated method stub
		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());

		User user = userRepository.findById(idU).get();
		FitnessUser fitness = user.getFitness();

		Food foodToRemove = foodRepository.findById(idF).get();

		List<FoodEaten> eatensToday = eatenRepository.findByFitnessIdAndDate(fitness.getId(), today);

		if (fitness != null) {
			for (FoodEaten fe : eatensToday) {
				if (eatensToday.contains(fe)) {
					eatensToday.remove(fe);
					this.eatenRepository.delete(fe);
					this.fitnessRespository.save(fitness);
					return true;
				}
			}
		}

		return false;
	}

	// remove all listToday of fooeEaten today
	public boolean removeAllEatenFoodListForFitnessUser(Long idU) {
		// TODO Auto-generated method stub
		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());

		User user = userRepository.findById(idU).get();
		FitnessUser fitness = user.getFitness();

		boolean deleteDone = false;
		if (fitness != null) {
			eatenRepository.deleteByFitnessIdAndDate(fitness.getId(), today);

			deleteDone = true;
			return deleteDone;
		}
		return deleteDone;
	}

}
