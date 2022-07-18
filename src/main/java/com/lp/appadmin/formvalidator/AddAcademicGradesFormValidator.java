package com.lp.appadmin.formvalidator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.lp.model.AcademicGrades;

public class AddAcademicGradesFormValidator implements Validator {

	// which objects can be validated by this validator
	@Override
	public boolean supports(Class<?> paramClass) {
		return AcademicGrades.class.equals(paramClass);
	}


	@Override
	public void validate(Object obj, Errors errors) {
		AcademicGrades agrades = (AcademicGrades) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "acedamicGradeName",
				"acedamicGradeName.required", "Acedamic Grade Name required");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scoreFrom",
				"scoreFrom.required", "Score Frome is required");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scoreTo",
				"scoreTo.required", "Score To is required");

		if (agrades.getAcademicId().equals("select")) {
			errors.rejectValue("academicId", "academicId",
					"select a Academic Performance");
		}
	}
}
