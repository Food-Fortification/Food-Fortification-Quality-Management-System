package org.path.fortification.utils;

import lombok.extern.slf4j.Slf4j;
import org.path.fortification.dto.requestDto.BatchDispatchMailRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MailUtils {

    @Value("${mail.recipients.list}")
    List<String> recipients;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendMail() {
        try {
            String message = "Dear Team,\n\nThe scheduled recompilation of data for the Super Monitor has been successfully completed.\n\nBest regards,\nRice Fortification Support.";

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipients.toArray(new String[0]));
            mailMessage.setText(message);
            mailMessage.setSubject("Scheduled Recompilation Completed - PATH");

            javaMailSender.send(mailMessage);
            log.info("Recompilation confirmation mail sent successfully");
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void sendMailUsingTemplate(String templateName, BatchDispatchMailRequestDto batchDispatchMailRequestDto, String subject) {
        try {
            Context context = new Context();
            context.setVariable("data", batchDispatchMailRequestDto); // Pass the DTO to the template under the name "data"

            String body = templateEngine.process(templateName, context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully using template: {}", templateName);

        } catch (MessagingException e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }

}

