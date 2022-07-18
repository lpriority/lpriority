package com.lp.appadmin.formvalidator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.lp.model.States;

public class AddStatesFormValidator implements Validator {

	// which objects can be validated by this validator
	@Override
	public boolean supports(Class<?> paramClass) {
		return States.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		States state = (States) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"name.required", "State Name required");
		if (state.getCountryId().equals("select")) {
			errors.rejectValue("countryId", "countryId", "select a Country");
		}
	}
}
