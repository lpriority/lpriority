package com.lp.login.formvalidator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lp.model.UserRegistration;

public class AdminReg3FormValidator implements Validator {
	private Pattern pattern;
	private Matcher matcher;
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

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qualification",
				"qualification.required", "qualification required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phonenumber",
				"phonenumber.required", "Phone number required.");
		// phone number validation
		if (!(user.getPhonenumber() != null && user.getPhonenumber().isEmpty())) {
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(user.getPhonenumber());
			if (!matcher.matches()) {
				errors.rejectValue("phonenumber", "phonenumber.incorrect",
						"Enter a correct phone number");
			}
		}
	}
}
