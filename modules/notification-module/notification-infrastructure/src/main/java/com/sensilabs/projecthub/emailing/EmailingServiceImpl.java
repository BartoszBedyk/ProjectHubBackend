package com.sensilabs.projecthub.emailing;


import com.sensilabs.projecthub.notification.EmailingService;
import com.sensilabs.projecthub.notification.NotificationRepository;
import com.sensilabs.projecthub.notification.model.Notification;
import com.sensilabs.projecthub.notification.model.NotificationParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailingServiceImpl implements EmailingService {


    private final EmailSender emailSender;
    private final NotificationRepository notificationRepository;



    public void send(Notification notification) {
        notification.increaseAttempts();
        try {
            emailSender.send(notification.getReceiver(), notification.getType().getSubject(), EmailingServiceImpl.toMap(notification.getParams()),
                    notification.getType().getTemplateId());
            notification.finalizeSent();
        } catch (Exception e) {
            e.getStackTrace();
           log.error("Email sending failed email: {}, Error message: {}", notification.getReceiver(), e.getMessage());
        }
        notificationRepository.save(notification);
    }


    public static Map<String, String> toMap(List<NotificationParam> list) {
        Map<String, String> paramsMap = new HashMap<>();
        for (NotificationParam param : list) {
            paramsMap.put(param.getName(), param.getValue());
        }
        return paramsMap;
    }


}
