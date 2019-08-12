package com.arm.BatchHa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import com.arm.BatchHa.bean.ReserveResponse;

@Configuration
public class JobScheduleWithBrokerApp {
		
		@Value("${jobbroker.reserve.url}")
		String brokerServiceUrl ;
		
		@Scheduled(cron = "0/5 * * * * ?")
		void jobperform(){
			String timeId = jobTimeId() ;
			String job1TagName = "job1"+timeId ;
			String job2TagName = "job2"+timeId ;
			String job3TagName = "job3"+timeId ;
			String job4TagName = "job4"+timeId ;
			
			List<String> jobList = new ArrayList<String>() ;
			jobList.add(job1TagName) ;
			jobList.add(job2TagName) ;
			jobList.add(job3TagName) ;
			jobList.add(job4TagName) ;
			
			System.out.println("##### EXECUTE JOB START") ;
			for(String job : jobList){
				ExecuteJob(job) ;
			}
			System.out.println("##### EXECUTE JOB END") ;
		}
		
		public void ExecuteJob(String tagName){
			if(canRunThis(tagName)){
				System.out.println(tagName  + " Run and Execute " + new Date());
				//TODO
				try {
					Thread.sleep(200);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}else{
				System.err.println(tagName  + " Skip  " + new Date());
			}
		}
		
		public boolean canRunThis(String tagName){
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("tagName", tagName);
			RestTemplate restTemplate = new RestTemplate() ;
			
			ReserveResponse response = restTemplate.getForObject(brokerServiceUrl, ReserveResponse.class,params) ;
			return response.getReserveResult().equalsIgnoreCase("Y") ;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss") ;
		String jobTimeId(){
				
			return sdf.format(new Date()) ;
		}
		
}
