package com.lp.appadmin.formvalidator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.lp.model.SubInterest;

public class SubInterestFormValidator implements Validator {

	// which objects can be validated by this validator
	@Override
	public boolean supports(Class<?> paramClass) {
		return SubInterest.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		SubInterest subInterest= (SubInterest) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subInterest",
				"subInterest.required", " Sub Interest required");

		if (subInterest.getInterestId().equals("select")) {
			errors.rejectValue("interestId", "interestId",
					"select an Interest");
		}
	}
}
