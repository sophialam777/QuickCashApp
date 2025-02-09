package com.example.iteration1;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    // Email account: quickcashcsci3130@gmail.com
    // Password: Group5csci3130
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL = "quickcashcsci3130@gmail.com";
    private static final String PASSWORD = "nkdh mtrm czrh yfar";

    public static void sendConfirmationEmail(String recipientEmail, String userName) {
        new SendEmailTask().execute(recipientEmail, userName);
    }

    private static class SendEmailTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String recipientEmail = params[0];
            String userName = params[1];

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(EMAIL, PASSWORD);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject("QuickCash - Account Confirmation");
                message.setText("Dear " + userName + ",\n\nYour account has been successfully created. " +
                        "Welcome to our QuickCash App!");

                Transport.send(message);

                Log.i("EmailUtil", "Confirmation email sent to " + recipientEmail);
                return true; // Email sent successfully
            } catch (MessagingException e) {
                Log.e("EmailUtil", "Error sending email", e);
                return false; // Email failed to send
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Log.i("EmailUtil", "Email sent successfully");
            } else {
                Log.e("EmailUtil", "Failed to send email");
            }
        }
    }
}
