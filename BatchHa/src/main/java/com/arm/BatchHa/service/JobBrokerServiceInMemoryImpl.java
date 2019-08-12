package com.arm.BatchHa.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class JobBrokerServiceInMemoryImpl implements JobBrokerService {

	Map<String,Object> dataStore = new ConcurrentHashMap<String,Object>() ;
	Object lockObject = new Object() ;
	@Override
	public String reserveJob(String tagName,String reservedBy) {
		// TODO Auto-generated method stub
		String reservedResult ;
		synchronized(lockObject){
			if(dataStore.containsKey(tagName)){
				reservedResult = "N" ;
			}else{
				Map<String,Object> data = new ConcurrentHashMap<String,Object>() ;
				data.put("tagName", tagName) ;
				data.put("reservedBy", reservedBy) ;
				data.put("reservedDate", new Date()) ;
				dataStore.put(tagName, data) ;
				reservedResult = "Y" ;	
			}
		}
		return reservedResult;
	}

}
