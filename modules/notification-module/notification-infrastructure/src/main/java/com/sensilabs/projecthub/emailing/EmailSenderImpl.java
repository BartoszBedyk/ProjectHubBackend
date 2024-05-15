package com.sensilabs.projecthub.emailing;

import com.google.api.services.gmail.Gmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.google.api.services.gmail.model.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class EmailSenderImpl implements EmailSender{

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    @Value("${spring.mail.username}")
    private String fromMail;

    private static final String USER_ID = "me";




    @Override
    public void send(String sendTo, String subject, Map<String, String> params, String templateId) throws GeneralSecurityException, IOException, MessagingException {


        Gmail service = new GmailService().getService();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setSubject(subject);

        Context context = new Context();
        Map<String, Object> templateContext = new HashMap<>();

        templateContext.put("toMail", sendTo);
        templateContext.put("sendTime", getTime());
        templateContext.putAll(params);

        context.setVariables(templateContext);

        String processedString = templateEngine.process(templateId, context);
        mimeMessageHelper.setText(processedString, true);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        mimeMessage.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().encodeToString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        service.users().messages().send(USER_ID, message).execute();

    }


    private String getTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss");
        return dateTime.format(formatter);
    }
}
