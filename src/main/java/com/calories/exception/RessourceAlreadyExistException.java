package com.calories.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.IM_USED)
public class RessourceAlreadyExistException extends Exception{

	 public RessourceAlreadyExistException(String message){
	        super(message);
	    }
}