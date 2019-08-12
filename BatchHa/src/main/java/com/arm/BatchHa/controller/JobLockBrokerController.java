package com.arm.BatchHa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arm.BatchHa.bean.ReserveResponse;
import com.arm.BatchHa.service.JobBrokerService;

@RestController	
@EnableScheduling
public class JobLockBrokerController {
	
			@Autowired
			JobBrokerService jobBroker;
			
			@GetMapping("/jobbroker/reserve")
			public ReserveResponse reservedJob(
					@RequestParam("tagName") String tagName ,
					HttpServletRequest request
					){
				String reservedBy = request.getRemoteAddr() ;
				ReserveResponse response = new ReserveResponse() ;
				String reservedResult = jobBroker.reserveJob(tagName,reservedBy) ;
				
				response.setReserveResult(reservedResult) ;
				return response ;
			} 
}
