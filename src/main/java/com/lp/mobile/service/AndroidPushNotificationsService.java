package com.lp.mobile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.lang3.StringUtils; 


import com.lp.admin.dao.AddStudentsToClassDAO;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.model.Student;
import com.lp.model.UserRegistration;


@Service
public class AndroidPushNotificationsService {

	//private static final String FIREBASE_SERVER_KEY = "AAAAmIL6PD8:APA91bH-pbS2peuBRZFFm38KYo0jbNrWeulcIMizzyRnzw456zm4wini3o8W4P6dzVwx1G1oXRrHePXJ6RegpZJpVdliAD-2V77yn94yjil1VUKNJqFdzmGz7bnAA9ykCwIKzw2uc6Er";
	private static final String FIREBASE_SERVER_KEY = "AAAAp-s52_s:APA91bHXqourrQ2uGeBD7Yl_P3TQpG8_anMMqwA5BihHBxDBtJURkqPDKwYAITBEdmdw98vlmOUqc_5lvvPJ3KEzqwQ5p0sVuL0exCReeSF685zjm47BokhdLNW2Yk-JPS_DyEoDONxV";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Autowired
	private AddStudentsToClassDAO addStudentsToClassDao;
	@Autowired
	private UserRegistrationDAO userRegistrationDao;
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
//	@Async
	public void sendAttendanceNotification(String title,long studentId,String status){
		@SuppressWarnings("unused")
		ResponseEntity<String> res;
		try{
		/*Student student=addStudentsToClassDao.getStudent(studentId.get(0));
		UserRegistration parentUserReg=userRegistrationDao.getUserRegistration(student.getUserRegistration().getParentRegId());
		String deviceToken=""+parentUserReg.getRegId();
		String msg="Ur Child "+student.getUserRegistration().getFirstName()+" Attendance Status="+status.get(0);
		res=send(deviceToken,title,msg);*/
		
			Student student=addStudentsToClassDao.getStudent(studentId);
			String msg="Ur Child "+student.getUserRegistration().getFirstName()+" Attendance Status="+status;
			String deviceToken="LPTest"+student.getUserRegistration().getParentRegId()+"Parent";
			res=send(deviceToken,title,msg);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendEventNotification(String title,long schoolId ,String event){
		@SuppressWarnings("unused")
		ResponseEntity<String> res;
		try{
		 	String msg=""+event;
			String deviceToken="Event"+schoolId+"Parent";
			res=send(deviceToken,title,msg);
		
	   }catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	public void sendSchoolAnnouncements(String title,long schoolId ,String announcement){
		@SuppressWarnings("unused")
		ResponseEntity<String> res;
		try{
		 	String msg=""+announcement;
			String deviceToken="Announcement"+schoolId+"Parent";
			res=send(deviceToken,title,msg);
		
	   }catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	public void sendScheduleClassNotification(String title,long studentId){
		@SuppressWarnings("unused")
		ResponseEntity<String> res;
		try{
			Student student=addStudentsToClassDao.getStudent(studentId);
		 	String msg="Child scheduled for new class";
			String deviceToken="Schedule"+student.getUserRegistration().getParentRegId()+"Parent";
			title=StringUtils.capitalize(title)+" Class";
			res=send(deviceToken,title,msg);
		
	   }catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	public void sendStudentResultsNotification(String title,long studentId){
		@SuppressWarnings("unused")
		ResponseEntity<String> res;
		String msg="";
		try{
			if(title.equalsIgnoreCase("assessments"))
		 	msg="New Assessment Results are available.";
			else if(title.equalsIgnoreCase("homeworks"))
			msg="New Homework Results are available.";
			else
			msg="New RTI Results are available.";	
			Student student=addStudentsToClassDao.getStudent(studentId);
			String deviceToken="Results"+student.getUserRegistration().getParentRegId()+"Parent";
			title=StringUtils.capitalize(title)+" Results";
			res=send(deviceToken,title,msg);
									
	   }catch(Exception e){
			e.printStackTrace();
			
		}
		
		
	}
	
	@Async
	public ResponseEntity<String> send(String deviceToken,String title,String msg) throws JSONException {

		//deviceToken="Parent";
		JSONObject body = new JSONObject();
		body.put("to","/topics/"+deviceToken);
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", title);
		notification.put("body", msg);
		
		JSONObject data = new JSONObject();
		data.put("Key-1", "JSA Data 1");
		data.put("Key-2", "JSA Data 2");

		body.put("notification", notification);
		body.put("data", data);

		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			String firebaseResponse = pushNotification.get();
			
			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	}

}