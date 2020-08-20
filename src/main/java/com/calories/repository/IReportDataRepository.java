package com.calories.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calories.model.ReportData;

@Repository
public interface IReportDataRepository extends JpaRepository<ReportData, Long> {

	    List<ReportData> findByFitnessIdAndDate( Long id , Date date);
	    
	    List<ReportData> findByFitnessIdAndDateBetween( Long id , Date from , Date to );
	 
	    List<ReportData> findByFitnessId(Long id);
}
