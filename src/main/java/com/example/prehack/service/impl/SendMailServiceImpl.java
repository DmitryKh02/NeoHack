package com.example.prehack.service.impl;

import com.example.prehack.exception.CreateMimeMessageException;
import com.example.prehack.service.SendMailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService {

    private final JavaMailSender emailSender;

    private final Configuration configurationEmail;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Override
    public void sendEmail(String email, String theme, String text) {
        log.info("[sendEmail] >> email: {}, theme: {}, text: {}...", email, theme, text.substring(0, 10));

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(emailFrom);
            helper.setTo(email);
            helper.setSubject(theme);

            String emailContent = getEmailContent(theme, text);
            helper.setText(emailContent, true);

        } catch (MessagingException | TemplateException | IOException e) {
            log.error("Error when creating a email");
            throw new CreateMimeMessageException("Error when creating a email, exception's message: " + e.getMessage());
        }

        emailSender.send(mimeMessage);

        log.info("[sendEmail] << result void, email: {}", mimeMessage);

    }

    private String getEmailContent(String theme, String textForUser) throws IOException, TemplateException {
        log.info("[sendEmail] >> theme: {}, textForUser: {}...", theme, textForUser.substring(0, 10));

        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        model.put("theme", theme);
        model.put("textForUser", textForUser);

        configurationEmail.getTemplate("email.ftlh").process(model, stringWriter);

        String emailContent = stringWriter.getBuffer().toString();

        log.info("[sendEmail] << result: {}", emailContent);

        return emailContent;
    }


}
