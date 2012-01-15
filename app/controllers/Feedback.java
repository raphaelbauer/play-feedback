package controllers;

import play.Play;
import play.data.validation.Email;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;
import ugot.recaptcha.Recaptcha;

public class Feedback extends Controller {

	public static void index() {
		render();
	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @param confirm
	 */
	public static void submit(
			@Required @Email String email,
			@Required String feedbacktext, 
			@Recaptcha String captcha) {

		// /check:
		checkAuthenticity();

		boolean hasErrors = true;

		if (Play.id.equals("test")) {

			// we are in test mode.. ignore wrong captcha
			if (validation.errors().size() == 1) {

				if (validation.errors().get(0).getKey().equals("captcha")) {
					hasErrors = false;
				}
			}

		} else {

			hasErrors = validation.hasErrors();

		}

		// save or display error
		if (hasErrors) {
			
			flash.error("feedback.error");
			params.flash("email");
			params.flash("feedbacktext");
			validation.keep();
			index();

		} else {
			
			//send email to manager of feedbacks:
			FeedbackEmail.sendOwner(email, feedbacktext);
			//Send confirmation to user that sent the mail:
			FeedbackEmail.sendConfirmation(email);
			

			sent();

		}

	}

	public static void sent() {

		render();
	}

}