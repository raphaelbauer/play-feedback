package controllers;

import java.util.Date;

import play.Play;
import play.mvc.Mailer;

public class FeedbackEmail extends Mailer {

	final static String EMAIL_FROM = "playfeedback.emailFrom";

	public static void sendOwner(String emailOfCustomer, String feedbackText) {

		String emailFrom = Play.configuration.getProperty(EMAIL_FROM, "");

		if (emailFrom.equals("")) {

			throw new RuntimeException(
					"playsienauser.emailFrom not set in applications.conf. Doing nothing...");
		} else {

			setSubject("Feedback received " + new Date(System.currentTimeMillis()));

			addRecipient(emailFrom);
			//add a reply to, so that the email client automatically picks the correct recipient...
			setReplyTo(emailOfCustomer);
			
			setFrom(emailFrom);

			send("Feedback/feedbackEmail", emailOfCustomer, feedbackText);
		}
	}

	public static void sendConfirmation(String email) {

		String emailFrom = Play.configuration.getProperty(EMAIL_FROM, "");

		if (emailFrom.equals("")) {

			throw new RuntimeException(
					"playsienauser.emailFrom not set in applications.conf. Doing nothing...");
		} else {

			setSubject("Feedback Received");

			String recipient = email;
			addRecipient(recipient);
			setFrom(emailFrom);

			send("Feedback/confirmation");
		}
	}

}