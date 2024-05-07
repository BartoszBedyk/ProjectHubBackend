package com.sensilabs.projecthub.emailing;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component

public class EmailSenderImpl implements EmailSender{

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("{spring.mail.username}")
    private String fromMail;

    @Override
    public void send(String sendTo, String subject, Map<String, String> params, String templateId) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setSubject(subject);

        Context context = new Context();
        Map<String, Object> templateContext = new HashMap<>();

        templateContext.put("toMail", sendTo);
        templateContext.put("sendTime", getTime());
        params.forEach((name, value) -> templateContext.put(name, value));

        context.setVariables(templateContext);

        String processedString = templateEngine.process(templateId, context);
        mimeMessageHelper.setText(processedString, true);
        mailSender.send(mimeMessage);

    }

    private String getTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss");
        return dateTime.format(formatter);
    }
}
