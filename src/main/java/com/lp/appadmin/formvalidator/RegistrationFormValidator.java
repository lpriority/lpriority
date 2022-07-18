package com.lp.appadmin.formvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.lp.model.UserRegistration;

public class RegistrationFormValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	String ID_PATTERN = "[0-9]+";
	String STRING_PATTERN = "[a-zA-Z]+";
	String MOBILE_PATTERN = "[0-9]{10}";

	// which objects can be validated by this validator
	@Override
	public boolean supports(Class<?> paramClass) {
		return UserRegistration.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UserRegistration user = (UserRegistration) obj;

		if (user.getSchoolId().equals("select")) {
			errors.rejectValue("schoolId", "schoolId",
					"Select a school");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId",
				"emialId.required", " Email id cannot be empty");

		if (!(user.getEmailId() != null && user.getEmailId().isEmpty())) {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(user.getEmailId());
			if (!matcher.matches()) {
				errors.rejectValue("emailId", "emailId.incorrect",
						"Enter a valid email");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
				"firstName.required", "First name required.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
				"lastName.required", "Last name required");
	}
}
