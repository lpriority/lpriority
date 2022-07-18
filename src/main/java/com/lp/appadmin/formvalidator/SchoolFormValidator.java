package com.lp.appadmin.formvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.lp.model.School;

public class SchoolFormValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	String MOBILE_PATTERN = "[0-9]{10}";

	// which objects can be validated by this validator
	@Override
	public boolean supports(Class<?> paramClass) {
		return School.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		School school = (School) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "schoolName",
				"schoolName.required", "schoolName required");

		if (school.getSchoolLevelId().equals("select")) {
			errors.rejectValue("schoolLevelId", "schoolLevelId.select",
					"select a School Level Id");
		}
		if (school.getSchoolTypeId().equals("select")) {
			errors.rejectValue("schoolTypeId", "schoolTypeId.select", "select a School TypeId");
		}
		
		if (school.getCountryId().equals("select")) {
			errors.rejectValue("countryId", "countryId.select",
					"select a country");
		}
		if (school.getStateId().equals("select")) {
			errors.rejectValue("stateId", "stateId.select", "select a state");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city",
				"city.required", "City required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber",
				"phoneNumber.required", "Phone number required.");
		// phone number validation
		if (!(school.getPhoneNumber() != null && school.getPhoneNumber()
				.isEmpty())) {
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(school.getPhoneNumber());
			if (!matcher.matches()) {
				errors.rejectValue("phoneNumber", "phoneNumber.incorrect",
						"Enter a correct phone number");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "faxNumber",
				"faxNumber.required", "Fax Number required");

		// fax number validation
		if (!(school.getFaxNumber() != null && school.getFaxNumber().isEmpty())) {
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(school.getFaxNumber());
			if (!matcher.matches()) {
				errors.rejectValue("faxNumber", "faxNumber.incorrect",
						"Enter a correct fax number");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noOfStudents",
				"noOfStudents.required", "Number of Students required.");
	}
}
