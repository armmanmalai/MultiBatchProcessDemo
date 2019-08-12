package com.arm.BatchHa.service;

import org.springframework.stereotype.Service;


public interface JobBrokerService {
	
		String reserveJob(String tagName,String reservedBy) ;
			
}
