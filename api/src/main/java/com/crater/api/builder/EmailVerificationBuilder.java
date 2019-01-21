package com.crater.api.builder;

import com.sendgrid.*;
import org.springframework.util.Assert;

import java.io.IOException;

public class EmailVerificationBuilder {

    private String fromEmail;
    private String templateId;
    private Personalization personalization = new Personalization();

    public EmailVerificationBuilder setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
        return this;
    }

    public EmailVerificationBuilder setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public EmailVerificationBuilder addPersonalization(String key, Object value) {
        personalization.addDynamicTemplateData(key, value);
        return this;
    }

    public EmailVerificationBuilder setToEmail(String email) {
        personalization.addTo(new Email(email));
        return this;
    }

    public Request buildRequest() throws IOException {
        Assert.notNull(fromEmail, "From email can't be null");
        Assert.notNull(templateId, "Template ID can't be null");
        Assert.notEmpty(personalization.getTos(), "To email's can't be null");
        Mail mail = new Mail();
        mail.setFrom(new Email(fromEmail));
        mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return request;
    }
}
