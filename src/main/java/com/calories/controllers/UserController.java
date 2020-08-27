package com.calories.controllers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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

import com.calories.config.request.SignupRequest;
import com.calories.exception.RessourceNotFoundException;
import com.calories.model.Exercise;
import com.calories.model.ExercisePerformed;
import com.calories.model.FitnessUser;
import com.calories.model.Food;
import com.calories.model.FoodEaten;
import com.calories.model.ReportData;
import com.calories.model.User;
import com.calories.model.Weight;
import com.calories.services.ExercisePerformedService;
import com.calories.services.ExerciseService;
import com.calories.services.FitnessUserService;
import com.calories.services.FoodEatenService;
import com.calories.services.FoodService;
import com.calories.services.ReportDataService;
import com.calories.services.UserService;
import com.calories.services.WeightService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/service")
public class UserController {

	@Autowired
	FitnessUserService fitnessService;

	@Autowired
	UserService userService;

	@Autowired
	ExercisePerformedService performedService;

	@Autowired
	ExerciseService exerciseService;

	@Autowired
	FoodService foodService;

	@Autowired
	FoodEatenService eatenService;

	@Autowired
	WeightService weightService;
	
	@Autowired
    ReportDataService reportService ; 
	
	
	@GetMapping("/user/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getAllUsers() {
		 return userService.getAllUsers();
	}
	
	@GetMapping("/user/reporttoday/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<ReportData> getReportDataForCurrentUSer(@PathVariable("id") Long id) {
		User connected = userService.getCurrent(id);
		 
		List<ReportData> report = reportService.getDataForcurrentUser(connected.getId());
		return report ;
	}
	@GetMapping("/user/getCurrent/{id}")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@PathVariable("id") Long id) {
		User connected = userService.getCurrent(id);
		return connected;
	}

	@GetMapping("/user/hasProfile/{id}")
	@PreAuthorize("hasRole('USER')")
	public boolean CurrentUserHasprofile(@PathVariable("id") Long id) {
		User connected = userService.getCurrent(id);
		
		return userService.hasProfileData(connected.getId());
	}
	@PostMapping("/user/{id}/addProfile") // OK
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity addProfile(@RequestBody FitnessUser fitness, @PathVariable("id") Long id) {

		FitnessUser fit = fitnessService.addDetailsProfileToUser(id, fitness);
		User user = fit.getUser();

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@PutMapping("/user/detailsUser/{id}/updatePassword")
	@PreAuthorize("hasRole('USER')")
	public User updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
			@PathVariable("id") Long id) throws RessourceNotFoundException {

		User current = userService.getCurrent(id);
		current = userService.updatePassword(id, oldPassword, newPassword);
		return current;
	}

	@PutMapping("/user/detailsUser/{id}/updateUsername") // OK
	@PreAuthorize("hasRole('USER')")
	public User updateUsername(@RequestParam String oldUsername, @RequestParam String newUsername,
			@PathVariable("id") Long id) {

		User current = userService.getCurrent(id);
		current = userService.updateUsername(id, oldUsername, newUsername);
		return current;
	}

	// search performedExercise between Date //OK
	@GetMapping("/user/{idU}/getListExercisePerformed/date")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public List<ExercisePerformed> getExercisePerformedForCurrentUserDateBetween(@PathVariable("idU") Long id,
			@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
			@RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to) {

		User connected = userService.getCurrent(id);
		
		List<ExercisePerformed> exercises = performedService.findPerformedPeriod(connected.getId(), from, to);
		return exercises;

	}

	// search all performedExercise without date or period /OK
	@GetMapping("/user/{idU}/getListExercisePerformed")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public List<ExercisePerformed> getExercisePerformedForCurrentUser(@PathVariable("idU") Long id) {

		User connected = userService.getCurrent(id);
		
		List<ExercisePerformed> exercises = performedService.getAllExercisePerformedByFitnessUser(connected.getId());
		return exercises;

	}

	// add exercise to USER
	// service/user/{idU}/fitnessDetails/addExercise/{idE}
	@PostMapping("/user/{idU}/fitnessDetails/addExercise/{idE}") // OK
	@PreAuthorize("hasRole('USER')")
	public boolean addExerciseToCurrentUser(@PathVariable("idE") Long idE, @PathVariable("idU") Long idU) {

		User connected = userService.getCurrent(idU);
		Exercise exercise = exerciseService.getOneById(idE);

		boolean add = false;
		add = performedService.addExercisePerformedToFitnessUser(idE, connected.getId());
		if (add == true) {

			return add;
		} else
			return add;
	}

	// get list exercise Done today //OK
	@GetMapping("/user/{idU}/getListExercisePerformedToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public List<ExercisePerformed> getExercisePerformedTodayForCurrentUser(@PathVariable("idU") Long id) {
		User current = userService.getCurrent(id);
		List<ExercisePerformed> exercises = performedService
				.getExercisePerformedDoneTodayForCurrentUser(current.getId());
		return exercises;
	}

	// count ExercisePerformed today //OK
	@GetMapping("/user/{idU}/getNbreOfExercisePerformedToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public int countExercisePerformedTodayForCurrentUser(@PathVariable("idU") Long id) {
		User current = userService.getCurrent(id);

		int nbreDone = this.getExercisePerformedTodayForCurrentUser(current.getId()).size();
		return nbreDone;
	}

	// count CaloriesBurned for Current User Today //OK
	@GetMapping("/user/{idU}/countCaloriesBurnedForCurrentUserToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public double countCaloriesBurnedTodayForCurrentUser(@PathVariable("idU") Long id) {
		User current = userService.getCurrent(id);
		//FitnessUser fitness = current.getFitness();
		List<ExercisePerformed> exercises = performedService
				.getExercisePerformedDoneTodayForCurrentUser(current.getId());

		double totalCalories = 0;
		for (ExercisePerformed e : exercises) {
			totalCalories += e.getExercise().getCaloriesBurned();
		}
		return totalCalories;
	}
	
	 

	// update email or usernameor password or three together //OK
	@PutMapping("/user/updateRequestSign/{id}")
	@PreAuthorize("hasRole('USER')")
	public User updateSignupRequest(@RequestBody SignupRequest request, @PathVariable("id") Long idU) {
		User current = userService.getCurrent(idU);
		return current = userService.updateRequestSign(idU, request);

	}

	// add food to USER
	// service/user/{idU}/fitnessDetails/addFood/{idF} //OK
	@PostMapping("/user/{idU}/fitnessDetails/addFood/{idF}") // OK
	@PreAuthorize("hasRole('USER')")
	public boolean addFoodToCurrentUser(@PathVariable("idF") Long idF, @PathVariable("idU") Long idU) {

		User connected = userService.getCurrent(idU);
		Food food = foodService.getOneById(idF);

		boolean add = false;
		add = eatenService.addFoodTOCurrentUser(idF, connected.getId());
		if (add == true) {

			return add;
		} else
			return add;
	}

	// get list FoodEaten today //ok
	@GetMapping("/user/{idU}/getListOfFoodEatenToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public List<FoodEaten> getListFoodEatenTodayForCurrentUser(@PathVariable("idU") Long id) {

		User current = userService.getCurrent(id);
		List<FoodEaten> eatenToday = eatenService.getAllFoodEatenForUserToday(current.getId());
		return eatenToday;

	}

	// OK
	//calories consumed
	@GetMapping("/user/{idU}/getCaloriesConsumedOfFoodEatenToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public double getCaloriesConsumedEatenTodayForCurrentUser(@PathVariable("idU") Long id) {

		User current = userService.getCurrent(id);
		double totalCaloriesConsumed = 0;
		
		totalCaloriesConsumed = eatenService.getCaloriesConsumedForUserToday(current.getId());
		return totalCaloriesConsumed;
	}
	
	// sel consumed
	@GetMapping("/user/{idU}/getSaltConsumedOfFoodEatenToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public double getSaltConsumedEatenTodayForCurrentUser(@PathVariable("idU") Long id) {

		User current = userService.getCurrent(id);
		double totalSaltsConsumed = 0;
		
		totalSaltsConsumed = eatenService.getSaltConsumedForUserToday(current.getId());
		return totalSaltsConsumed;
	}

	// fibres consumed 
	@GetMapping("/user/{idU}/getFibresConsumedOfFoodEatenToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public double getFibresConsumedEatenTodayForCurrentUser(@PathVariable("idU") Long id) {

		User current = userService.getCurrent(id);
		double totalFibresConsumed = 0;
		
		totalFibresConsumed = eatenService.getFibresConsumedForUserToday(current.getId());
		return totalFibresConsumed;
	}
	
	// proteines consumed
	@GetMapping("/user/{idU}/getProteinsConsumedOfFoodEatenToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public double getProteinsConsumedEatenTodayForCurrentUser(@PathVariable("idU") Long id) {

		User current = userService.getCurrent(id);
		double totalProteinsConsumed = 0;
		
		totalProteinsConsumed = eatenService.getProteinsConsumedForUserToday(current.getId());
		return totalProteinsConsumed;
	}
	
	// vitamines consumed
	@GetMapping("/user/{idU}/getVitaminesConsumedOfFoodEatenToday")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	public double getVitaminesConsumedEatenTodayForCurrentUser(@PathVariable("idU") Long id) {

		User current = userService.getCurrent(id);
		double totalVitaminesConsumed = 0;
		
		totalVitaminesConsumed = eatenService.getVitaminesConsumedForUserToday(current.getId());
		return totalVitaminesConsumed;
	}
	
	// get time done today for current user //OK
	@GetMapping("/user/{idU}/getTimeDoneExercisePerformedForCurrentUser")
	@PreAuthorize("hasRole('USER')")
	public double countTimeDoneForCurrentUserAndToday(@PathVariable("idU") Long id) {

		User current = userService.getCurrent(id);
		double totalTimeDone = 0;

		totalTimeDone = performedService.countTimeDoneForCurrentUserAndToday(current.getId());

		return totalTimeDone;
	}

	// delete one food from today list , only current date
	@DeleteMapping("/user/{idU}/removeSelectFoodFromCurrentUser/Food/{idF}")
	@PreAuthorize("hasRole('USER')")
	public boolean removeFoodEatenFromCurrentUserAndToday(@PathVariable("idU") Long idU,
			@PathVariable("idF") Long idF) {

		User current = userService.getCurrent(idU);
		
		boolean removeDone = false;
		removeDone = eatenService.removeEatenFoodFromListForFitnessUser(idF, current.getId());
		if (removeDone == true) {

			return removeDone;
		} else

			return removeDone;
	}
	
	// delete oneExercise from listToday 
	@DeleteMapping("/user/{idU}/removeSelectExerciseFromCurrentUser/Exercise/{idE}")
	@PreAuthorize("hasRole('USER')")
	public boolean removeExercisePerformedFromCurrentUserAndToday(@PathVariable("idU") Long idU,
			@PathVariable("idE") Long idE) {

		User current = userService.getCurrent(idU);
		
		boolean removeDone = false;
		removeDone = performedService.removeExercisePerformedFromFitnessUser(idE, current.getId());
		if (removeDone == true) {

			return removeDone;
		} else

			return removeDone;
	}
	
	

	@Transactional
	@DeleteMapping("/user/{idU}/removeAllFoodEatenTodayListFromCurrentUser")
	@PreAuthorize("hasRole('USER')")
	public boolean removeCuurentTodayListFoodEatenFromCurrentUserAndToday(@PathVariable("idU") Long idU) {

		boolean removeDone = false;
		removeDone = eatenService.removeAllEatenFoodListForFitnessUser(idU);
		if (removeDone == true) {

			return removeDone;
		} else

			return removeDone;
	}
	
	
	@Transactional
	@DeleteMapping("/user/{idU}/removeAllExercisePerformedTodayListFromCurrentUser")
	@PreAuthorize("hasRole('USER')")
	public boolean removeCuurentTodayListExercisePerformedFromCurrentUserAndToday(@PathVariable("idU") Long idU) {

		boolean removeDone = false;
		
		removeDone = performedService.removeAllExercisePerformedFromFitnessUser(idU);
		
		if (removeDone == true) {

			return removeDone;
		} else

			return removeDone;
	}
	

	// add weight to user
	@PostMapping("/user/{idU}/fitnessDetails/addOrUpdateWeight") // OK
	@PreAuthorize("hasRole('USER')")
	public User addOrUpdateWeightToCurrentUser(@PathVariable("idU") Long idU, @RequestParam Double newWeight) {

		User current = userService.getCurrent(idU);
		current = weightService.addOrUpdateWeightToCurrentUser(idU, newWeight);

		return current;
	}

	// get all weight for current user //OK
	@GetMapping("/user/{idU}/getWeightDataForCurrentUser")
	@PreAuthorize("hasRole('USER')")
	public List<Weight> getListWeightForCurrentUser(@PathVariable("idU") Long id) {
		
		User current = userService.getCurrent(id);
		List<Weight> listWeight = weightService.getDataWeightForCurrentUser(id);
		return listWeight ;
	}
				
}
