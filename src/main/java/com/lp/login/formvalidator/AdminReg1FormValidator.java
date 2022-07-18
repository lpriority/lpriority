package com.lp.login.formvalidator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lp.model.UserRegistration;

public class AdminReg1FormValidator implements Validator {

	// which objects can be validated by this validator
	@Override
	public boolean supports(Class<?> paramClass) {
		return UserRegistration.class.equals(paramClass);
	}

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	String ID_PATTERN = "[0-9]+";
	String STRING_PATTERN = "[a-zA-Z]+";
	String MOBILE_PATTERN = "[0-9]{10}";

	@Override
	public void validate(Object obj, Errors errors) {
		UserRegistration user = (UserRegistration) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
				"title.required", "Title required.");
		if (user.getTitle().equals("select")) {
			errors.rejectValue("title",
					"title.select", "select a Title");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
				"firstName.required", "First Name required.");

		// input string conatains characters only
		if (!(user.getFirstName() != null && user.getFirstName().isEmpty())) {
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(user.getFirstName());
			if (!matcher.matches()) {
				errors.rejectValue("firstName", "firstName.containNonChar",
						"Enter a valid First Name");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
				"lastName.required", "Last Name required.");
		// input string conatains characters only
		if (!(user.getLastName() != null && user.getLastName().isEmpty())) {
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(user.getLastName());
			if (!matcher.matches()) {
				errors.rejectValue("lastName", "lastName.containNonChar",
						"Enter a valid Last Name");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address1",
				"address1.required", "Address1 is required.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryId",
				"countryId.required", "Country required.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateId",
				"stateId.required", "State required.");

		if (user.getCountryId().equals("select")) {
			errors.rejectValue("countryId", "countryId.select",
					"select a Country");
		}

		if (user.getStateId().equals("select")) {
			errors.rejectValue("stateId", "stateId.select", "select a State");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city",
				"city.required", " City required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode",
				"zipcode.required", "Zipcode required.");

		// input string conatains numeric values only
		if (user.getZipcode() != null) {
			pattern = Pattern.compile(ID_PATTERN);
			matcher = pattern.matcher(user.getZipcode());
			if (!matcher.matches()) {
				errors.rejectValue("zipcode", "zipcode.incorrect",
						"Enter a numeric value");
			}
		}
		// input string can not exceed that a limit
		if (user.getZipcode().toString().length() > 5
				|| user.getZipcode().toString().length() < 5) {
			errors.rejectValue("zipcode", "id.exceed",
					"Id should contain 5 digits");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId",
				"emailId.required", "Email Id required.");
		// email validation in spring
		if (!(user.getEmailId() != null && user.getEmailId().isEmpty())) {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(user.getEmailId());
			if (!matcher.matches()) {
				errors.rejectValue("emailId", "emailId.incorrect",
						"Enter a valid email");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"password.required", "Password required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"confirmPassword.required", " Confirm Password required.");
		// password matching validation
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "password.mismatch",
					"Password does not match");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "securityQuestionId",
				"securityQuestionId.required", "Security question required.");
		if (user.getSecurityQuestionId().equals("select")) {
			errors.rejectValue("securityQuestionId",
					"securityQuestionId.select", "select a Question");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "answer",
				"answer.required", " Answer Required.");
	}
}
