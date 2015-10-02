package sample;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Test;

import sample.model.User;

public class ValidationTest {

	@Test
	public void ifNameIsNull_nameValidationFails() {
		User user = new User();
		user.setWorking(true);
		user.setAboutMe("Its all about me!!");
		user.setAge(50);
		 
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();		
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		Iterator<ConstraintViolation<User>> iter = violations.iterator();

		while (iter.hasNext()) {
			ConstraintViolation<User> cv = iter.next();
		}

		Assert.assertEquals(violations.isEmpty(), false);
	}

	@Test
	public void ifWorkingIsFalse_workingValidationFails() {
		User user = new User();
		user.setName("MyName");		
		user.setAboutMe("Its all about me!!");
		user.setAge(50);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();		
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		Iterator<ConstraintViolation<User>> iter = violations.iterator();

		while (iter.hasNext()) {
			ConstraintViolation<User> cv = iter.next();
		}

		Assert.assertEquals(violations.isEmpty(), false);
	}

	@Test
	public void ifAgeNotRange_ageValidationFails() {
		User user = new User();
		user.setName("MyName");		
		user.setAboutMe("Its all about me!!");
		user.setAge(8);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();		
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		Iterator<ConstraintViolation<User>> iter = violations.iterator();

		while (iter.hasNext()) {
			ConstraintViolation<User> cv = iter.next();
		}

		Assert.assertEquals(violations.isEmpty(), false);
	}
}
