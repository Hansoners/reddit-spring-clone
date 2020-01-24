package com.example.springredditdemo.service;

import com.example.springredditdemo.exception.RedditException;
import com.example.springredditdemo.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    void sendMail(NotificationEmail notificationEmail) throws RedditException {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("reddit-clone@redditclone.com");
            helper.setTo(notificationEmail.getRecipient());
            helper.setSubject(notificationEmail.getSubject());
            helper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Activation sent.");
        } catch (MailException e) {
            log.info(String.valueOf(e));
            throw new RedditException("Exception occurred sending mail to " + notificationEmail.getRecipient());
        }
    }
}
