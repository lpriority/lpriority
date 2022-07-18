package com.lp.admin.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.lp.admin.service.AdminService;
import com.lp.admin.service.AnnouncementsNEventsService;
import com.lp.appadmin.dao.UserRegistrationDAO;
import com.lp.common.service.FileService;
import com.lp.mobile.service.AndroidPushNotificationsService;
import com.lp.model.Announcements;
import com.lp.model.EventStatus;
import com.lp.model.Events;
import com.lp.model.UserRegistration;
import com.lp.utils.FileUploadUtil;
import com.lp.utils.WebKeys;

@Controller
public class AnnouncementsNEventsController extends WebApplicationObjectSupport {

	@Autowired
	private AnnouncementsNEventsService announceService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private FileService fileservice;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserRegistrationDAO userRegistrationDAO;
	@Autowired
	private AndroidPushNotificationsService apns;
	

	static final Logger logger = Logger.getLogger(AnnouncementsNEventsController.class);

	/* This method redirects to Admin Add Announcements Page */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/goToAddAnnouncementsPage", method = RequestMethod.GET)
	public ModelAndView goToAddAnnouncementsPage(HttpSession session,
			@ModelAttribute Announcements announcements) {
		UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Admin/AddAnnouncements", "announcements", new Announcements());
		try{
			session.removeAttribute("operation");
			List<Announcements> announce = (List<Announcements>) announceService.getAnnouncementsBySchool(userReg.getSchool());
			model.addObject("userReg", userReg);
			model.addObject("announcementsList", announce);
			model.addObject("operation","add");
		}catch (Exception e) {
			logger.error("Error in goToAddAnnouncementsPage() of AnnouncementsNEventsController"
					+ e);
			e.printStackTrace();
		}
		return model;
	}

	/* This method is used to save announcements to the data base */

	@SuppressWarnings("unchecked")
	@RequestMapping("/saveAnnouncements")
	public ModelAndView saveAnnouncements(
			@ModelAttribute("announcements") Announcements announcements,
			BindingResult result, HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/AddAnnouncements",
				"announcements", new Announcements());
		try {
			session.setAttribute("operation",announcements.getOperationMode());
			if (announcements.getOperationMode().equals("add")
					|| announcements.getOperationMode().equals("edit")) {

				@SuppressWarnings("unused")
				boolean flag = announceService.saveorUpdateAnnouncements(
						announcements, session);				
				session.setAttribute("status",
						"Announcement saved/updated successfully.");
			} else {
				announceService.deleteAnnouncements(announcements);
				session.setAttribute("status",
						"Announcement deleted successfully.");
			}
			UserRegistration user = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);			
			List<Announcements> announce = (List<Announcements>) announceService
					.getAnnouncementsBySchool(user.getSchool());
			model.addObject("announcementsList", announce);
		} catch (Exception e) {
			logger.error("Error in saveAnnouncements() of AnnouncementsNEventsController"
					+ e);
			e.printStackTrace();
		}
		return model;
	}

	/* This method is to get Announcements List */

	@RequestMapping(value = "/getAnnouncements", method = RequestMethod.GET)
	public View getAnnouncements(
			@RequestParam("announcementId") long announcementId, Model model)
			throws Exception {
		Announcements announceObj = announceService
				.getAnnouncements(announcementId);
		model.addAttribute("announceObj", announceObj);
		model.addAttribute("announcDate", new SimpleDateFormat("MM/dd/yyyy")
				.format(announceObj.getAnnounceDate()));
		return new MappingJackson2JsonView();
	}

	/* This method is to compare Announcement  */
	@RequestMapping(value = "/compareAnnouncement", method = RequestMethod.GET)
	public View compareAnnouncement(
			@RequestParam("announcementName") String announcementName,
			Model model) throws Exception {
		boolean flag = announceService.checkAnnouncements(announcementName);
		model.addAttribute("status", flag);
		return new MappingJackson2JsonView();
	}

	/* This method redirects to Admin Add Events Page */

	@RequestMapping(value = "/goToAddEventsPage", method = RequestMethod.GET)
	public ModelAndView goToAddEventsPage(HttpSession session,
			@ModelAttribute Events Events) {
		UserRegistration userReg = (UserRegistration) session
				.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		ModelAndView model = new ModelAndView("Admin/AddEvents", "events",
				new Events());
		model.addObject("userReg", userReg);
		session.removeAttribute("operation");
		List<Events> list = (List<Events>) announceService.getEventsBySchool(userReg.getSchool());
		model.addObject("eventsList", list);
		return model;
	}

	/* This method is used to get the events List */

	@RequestMapping(value = "/getEvents", method = RequestMethod.GET)
	public View getEvents(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("eventId") long eventId, Model model)
			throws Exception {
		Events eventObj = announceService.getEvents(eventId);
		model.addAttribute("eventObj", eventObj);
		model.addAttribute("announcementDate", new SimpleDateFormat(
				"MM/dd/yyyy").format(eventObj.getAnnouncementDate()));
		model.addAttribute("scheduleDate", new SimpleDateFormat("MM/dd/yyyy")
				.format(eventObj.getScheduleDate()));
		model.addAttribute("lastDate", new SimpleDateFormat("MM/dd/yyyy")
				.format(eventObj.getLastDate()));
		String[] eventTime = eventObj.getEventTime().split("@");
		model.addAttribute("hours", eventTime[0]);
		model.addAttribute("minutes", eventTime[1]);
		model.addAttribute("timeMeridian", eventTime[2]);
		return new MappingJackson2JsonView();
	}

	/* This method is used to save Events to the data base */

	@RequestMapping("/saveEvents")
	public ModelAndView saveEvents(
			@ModelAttribute("events") Events events,
			BindingResult result, HttpSession session) {
		ModelAndView model = new ModelAndView("Admin/AddEvents", "events",new Events());
		try {
			String eventTime = events.getHours() + "@" + events.getMinutes()
					+ "@" + events.getTimeMeridian();
			events.setEventTime(eventTime);
			session.setAttribute("operation",events.getOperationMode());
			if (events.getOperationMode().equals("delete")) {
				announceService.deleteEvents(events);
				session.setAttribute("status", "Events deleted successfully.");
			} else {
				announceService.saveorUpdateEvents(events, session);
				session.setAttribute("status",
						"Events added/edited successfully.");
			}
			UserRegistration user = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);			
			List<Events> list = announceService.getEventsBySchool(user.getSchool());
			model.addObject("eventsList", list);

		} catch (Exception e) {
			logger.error("Error in saveEvents() of EventsNEventsController" + e);
			e.printStackTrace();
		}
		return model;
	}

	/* This method is to check events */
	@RequestMapping(value = "/compareEvent", method = RequestMethod.GET)
	public View compareEvent(@RequestParam("eventName") String eventName,@RequestParam("eventId") long eventId,
			Model model,HttpSession session) throws Exception {
		UserRegistration user = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
		boolean flag = announceService.checkEvents(eventName, eventId, user.getSchool().getSchoolId());
		model.addAttribute("status", flag);
		return new MappingJackson2JsonView();
	}
	

	@RequestMapping(value = "/openAnnouncementDialog", method = RequestMethod.GET)
	public ModelAndView openAnnouncementDialog(
			@RequestParam("announcementId") long announcementId,
			@RequestParam("notificationStatusId") long notificationStatusId,
			HttpSession session,
			@ModelAttribute Announcements announcements,
			ModelAndView model) throws Exception {
		Announcements announceObj = new Announcements();
		if(announcementId > 0){
			announceObj = announceService.getAnnouncements(announcementId);
			model = new ModelAndView("Ajax/Admin/add_announcement","announcement", announceObj);
			UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);	
			if (!(userReg.getUser().getUserType().equals(WebKeys.LP_USER_TYPE_ADMIN) || userReg.getUser().getUserType().equals(WebKeys.LP_USER_TYPE_APP_MANAGER))) {
				announceService.setNotificationReadStatus(notificationStatusId);
			}
			model.addObject("announcDate", new SimpleDateFormat("MM/dd/yyyy").format(announceObj.getAnnounceDate()));
			model.addObject("operation", "edit");
		}else{
			model = new ModelAndView("Ajax/Admin/add_announcement","announcement", new Announcements());
			model.addObject("operation", "add");
		}

		UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		String folderPath = FileUploadUtil.getSchoolCommonFilesPath(userRegistration.getSchool(), request);
		String path = folderPath+File.separator + WebKeys.SCHOOL_ANNOUNCEMENTS_FOLDER + File.separator;
		model.addObject("path", path);
		model.addObject("announceObj", announceObj);
		model.addObject("createdFors", adminService.getUserTypesForAnnouncements());
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveOrUpdateAnnouncement")
	public View saveOrUpdateAnnouncement(
			@ModelAttribute("announcement") Announcements announcements,
			BindingResult result, HttpSession session, Model model, HttpServletRequest request) {
		try {
			boolean flag1 = false;
			String mode = "";
			List<UserRegistration> userRegLt = null;
			Announcements previousObj = null;
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;	
			MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();
			session.setAttribute("operation",announcements.getOperationMode());
			long userTypeId = announcements.getCreatedFor().getUserTypeid();
			long schoolId = userRegistration.getSchool().getSchoolId();
			long previousUserTypeId = 0;
			if(userTypeId == 0){
				announcements.setCreatedFor(null);
			}
			if(announcements.getAnnouncementId() == 0){
				mode = "create";
			}else{
				previousObj = announceService.getAnnouncements(announcements.getAnnouncementId());
				if(previousObj != null){
					if(previousObj.getFileName() != null){
						announcements.setFileName(previousObj.getFileName());
					}
				}
			}

			if (announcements.getOperationMode().equals("add")
					|| announcements.getOperationMode().equals("edit")) {

				flag1 = announceService.saveorUpdateAnnouncements(announcements, session);
				if(flag1){
					long announcementId = announcements.getAnnouncementId();
					if(announcementId > 0){
						if(userTypeId > 0)
							userRegLt = userRegistrationDAO.getUserRegistrationListBySchool(schoolId, userTypeId);
						else
							userRegLt = userRegistrationDAO.getAllUserRegistrationsBySchoolId(schoolId);
						
						if(mode.equalsIgnoreCase("create")){
							boolean resp = announceService.saveNotificationStatus(userRegLt, announcements);
							if(resp)
								model.addAttribute("status","Announcement Saved Successfully");
							else
								model.addAttribute("status","Failed");
						}else{
							if(previousObj.getCreatedFor() != null)
								previousUserTypeId = previousObj.getCreatedFor().getUserTypeid();
							if(previousUserTypeId != userTypeId){
								announceService.deleteNotificationStatus(announcements.getAnnouncementId());
								if(userTypeId > 0)
									userRegLt = userRegistrationDAO.getUserRegistrationListBySchool(schoolId, userTypeId);
								else
									userRegLt = userRegistrationDAO.getAllUserRegistrationsBySchoolId(schoolId);
								boolean resp = announceService.saveNotificationStatus(userRegLt, announcements);
								if(resp)
									model.addAttribute("status","Announcement Saved Successfully");
								else
									model.addAttribute("status","Failed");
							}
							model.addAttribute("status","Announcement Saved Successfully");
						}
					}
				}else{
					model.addAttribute("status","Failed");
				}
			} else {
				announceService.deleteAnnouncements(announcements);
				model.addAttribute("status", "Announcement deleted successfully.");
				session.setAttribute("status",
						"Announcement deleted successfully.");
			}
			if(flag1)
			{
				@SuppressWarnings("unused")
				boolean flag = fileservice.uploadAnnoucementAttachement(map, announcements);
			}
			UserRegistration user = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);			
			List<Announcements> announce = (List<Announcements>) announceService
					.getAnnouncementsBySchool(user.getSchool());
			model.addAttribute("announcementsList", announce);
		} catch (Exception e) {
			logger.error("Error in saveOrUpdateAnnouncement() of AnnouncementsNEventsController"
					+ e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}

	@RequestMapping(value = "/deleteAnnouncement", method = RequestMethod.GET)
	public View deleteAnnouncement(
			@RequestParam("announcementId") long announcementId,
			HttpSession session, 
			Model model) {
		try {
			if(announcementId > 0){
				Announcements announceObj = announceService.getAnnouncements(announcementId);
				announceService.deleteAnnouncements(announceObj);
				model.addAttribute("status", "Deleted");
			}else{
				model.addAttribute("status", "Error Occured");
			}
		} catch (Exception e) {
			model.addAttribute("status", "Error Occured");
			logger.error("Error in deleteAnnouncement() of AnnouncementsNEventsController"+ e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/openEventDialog", method = RequestMethod.GET)
	public ModelAndView openEventDialog(
			@RequestParam("eventId") long eventId,
			HttpSession session,
			ModelAndView model) throws Exception {
		
		if(eventId > 0){
			Events eventObj = announceService.getEvents(eventId);
			model = new ModelAndView("Ajax/Admin/add_event","event", eventObj);
			model.addObject("operation", "edit");
			model.addObject("eventObj", eventObj);
			model.addObject("announcementDate", new SimpleDateFormat(
					"MM/dd/yyyy").format(eventObj.getAnnouncementDate()));
			model.addObject("scheduleDate", new SimpleDateFormat("MM/dd/yyyy")
					.format(eventObj.getScheduleDate()));
			model.addObject("lastDate", new SimpleDateFormat("MM/dd/yyyy")
					.format(eventObj.getLastDate()));
			String[] eventTime = eventObj.getEventTime().split("@");
			model.addObject("hours", eventTime[0]);
			model.addObject("minutes", eventTime[1]);
			model.addObject("timeMeridian", eventTime[2]);
		}else{
			model = new ModelAndView("Ajax/Admin/add_event","event", new Events());
			model.addObject("operation", "add");
		}
		return model;
	}
	
	@RequestMapping("/saveOrUpdateEvent")
	public View saveOrUpdateEvent(
			@ModelAttribute("events") Events events,
			BindingResult result,
			HttpSession session,
			Model model) {
		try {
			List<UserRegistration> parentRegIds=new ArrayList<UserRegistration>();
			UserRegistration userRegistration = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
			long userTypeId=4;
			String eventTime = events.getHours() + "@" + events.getMinutes()
					+ "@" + events.getTimeMeridian();
			events.setEventTime(eventTime);
			session.setAttribute("operation",events.getOperationMode());
			List<UserRegistration> userRegLt = userRegistrationDAO.getAllUserRegistrationsBySchoolId(userRegistration.getSchool().getSchoolId());
			List<EventStatus> eventStatus = new ArrayList<EventStatus>();
			if(events.getEventId() <= 0){
				eventStatus = announceService.getEventStatus(userRegLt, events);
			}
			events.setEventStatus(eventStatus);
			boolean flag = announceService.saveorUpdateEvents(events, session);
			if(flag){
				model.addAttribute("status","Events added/edited successfully");
				
			}else{
				model.addAttribute("status","Failed");
			}

		} catch (Exception e) {
			logger.error("Error in saveOrUpdateEvent() of EventsNEventsController" + e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
	
	@RequestMapping(value = "/deleteEvent", method = RequestMethod.GET)
	public View deleteEvent(
			@RequestParam("eventId") long eventId,
			HttpSession session, 
			Model model) {
		try {
			if(eventId > 0){
				Events eventObj = announceService.getEvents(eventId);
				announceService.deleteEvents(eventObj);
				model.addAttribute("status", "Deleted");
			}else{
				model.addAttribute("status", "Error Occured");
			}
		} catch (Exception e) {
			model.addAttribute("status", "Error Occured");
			logger.error("Error in deleteEvent() of AnnouncementsNEventsController"+ e);
			e.printStackTrace();
		}
		return new MappingJackson2JsonView();
	}
}
