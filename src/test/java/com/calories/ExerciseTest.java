/**
 * 
 */
package com.calories;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.calories.model.CategoryExercise;
import com.calories.model.Exercise;
import com.calories.model.ExercisePerformed;
import com.calories.repository.IExercisePerformedRepository;
import com.calories.repository.IExerciseRepository;

/**
 * @author nvilla
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaloriesServiceV0Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExerciseTest {

	@Autowired
	IExerciseRepository repositoryExercise;

	@Test
	public void addExercise() {
		CategoryExercise catTest = new CategoryExercise();
		Exercise exeTest = new Exercise("name-test", "unkown.jpeg", 30, 200.00, catTest);
		assertNull(exeTest.getId());
		repositoryExercise.save(exeTest);
		assertNotNull(exeTest.getId());
	}

	@Test
	public void testGetAllExercises() {

	}

}
