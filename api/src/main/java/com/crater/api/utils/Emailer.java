package com.crater.api.utils;

import com.crater.api.builder.EmailVerificationBuilder;
import com.sendgrid.APICallback;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.springframework.stereotype.Component;

@Component
public class Emailer {

    private final SendGrid sendGrid;

    private String craterFromEmail;
    private String templateId;

    public Emailer() {
        sendGrid = new SendGrid(System.getenv("SENDGRID_API_KEY"));
    }

    public void sendVerificationEmail(String to, String token) {
        try {
            Request request = new EmailVerificationBuilder().setFromEmail(craterFromEmail).setTemplateId(templateId)
                    .setToEmail(to).addPersonalization("token", token)
                    .buildRequest();
            sendGrid.attempt(request, new APICallback() {
                @Override
                public void error(Exception ex) {

                }

                @Override
                public void response(Response response) {

                }
            });
        } catch (Exception e) {

        }
    }
}
