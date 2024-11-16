package com.zunaidhossain.fyi_its_anonymous.Service.Impl;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;
import com.zunaidhossain.fyi_its_anonymous.Service.EmailService;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // Set true for HTML content
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String createEmailContent(List<Feedback> unreadMessages, String name) {
        StringBuilder content = new StringBuilder("<b><i>Hi "+name+"!<i><b><br>You have the following unread messages:\n<ul>");
        for (Feedback message : unreadMessages) {
            content.append("<li>").append((message.getMsg()), 0, 10).append("....</li>");
        }
        content.append("</ul>");
        content.append("Thanks & Regards <br> <i>FYI: It's Anonymous<i> <br> An anonymous feedback receiving application");
        return content.toString();
    }
}
