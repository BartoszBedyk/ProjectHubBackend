package com.sensilabs.projecthub.notification.configurations.mail;

import com.sensilabs.projecthub.notification.forms.NotificationChannel;
import com.sensilabs.projecthub.notification.model.Notification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.engine.TemplateModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailingService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("{spring.mail.username}")
    private String fromMail;

    @Async
    public void sendMailFixed(Notification notification) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setTo(notification.getReceiver());
        Context context = new Context();
        Map<String, Object> templateModel = new HashMap<>();

        if (notification.getChannel() == NotificationChannel.EMAIL) {

            switch (notification.getType()) {
                case ACCOUNT_CREATE -> {

                    mimeMessageHelper.setSubject("ProjectHub account created");

                    templateModel.put("email", notification.getReceiver());
                    templateModel.put("firstName", notification.getParams().get(0).getValue());
                    templateModel.put("lastName", notification.getParams().get(1).getValue());
                    templateModel.put("time", notification.getCreatedOn());
                    templateModel.put("sendTime", getTime());
                    context.setVariables(templateModel);

                    String processedString = templateEngine.process("createAccountTemplate", context);
                    mimeMessageHelper.setText(processedString, true);
                    mailSender.send(mimeMessage);
                }
                case PASSWORD_RESET -> {

                    mimeMessageHelper.setSubject("ProjectHub password reset");

                    templateModel.put("email", notification.getReceiver());
                    templateModel.put("firstName", notification.getParams().get(0).getValue());
                    templateModel.put("lastName", notification.getParams().get(1).getValue());
                    templateModel.put("time", notification.getCreatedOn());
                    templateModel.put("sendTime", getTime());
                    context.setVariables(templateModel);


                    String processedString = templateEngine.process("resetPasswordTemplate", context);
                    mimeMessageHelper.setText(processedString, true);

                    mailSender.send(mimeMessage);
                }
            }
        }
    }


    private String getTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm:ss");
        return dateTime.format(formatter);
    }
}
