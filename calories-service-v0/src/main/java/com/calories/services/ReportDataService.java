package com.calories.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calories.model.FitnessUser;
import com.calories.model.ReportData;
import com.calories.model.User;
import com.calories.repository.IFitnessUserRepository;
import com.calories.repository.IReportDataRepository;
import com.calories.repository.UserRepository;

@Service
public class ReportDataService {

	@Autowired
	IFitnessUserRepository fitnessRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	IReportDataRepository reportRepository;

	// get Report data for connected user
	public List<ReportData> getDataForcurrentUser(Long id) {

		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		
		User connected = userRepository.findById(id).get();
		//FitnessUser fitness = fitnessRepository.findById(id).get();

		List<ReportData> report = new ArrayList<ReportData>();
		report = reportRepository.findByFitnessIdAndDate(connected.getFitness().getId() , today);
		
		return report;
	}

	public ReportData addReportDataToCurrentUSer( Long id) {
		java.util.Date date = new java.util.Date();
		java.sql.Date today = new java.sql.Date(date.getTime());
		
		User connected = userRepository.findById(id).get();
		 
		
		
		return null;
	}
}
