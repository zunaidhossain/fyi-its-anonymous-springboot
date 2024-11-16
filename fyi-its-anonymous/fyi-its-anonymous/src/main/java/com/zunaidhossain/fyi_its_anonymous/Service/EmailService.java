package com.zunaidhossain.fyi_its_anonymous.Service;

import com.zunaidhossain.fyi_its_anonymous.Entity.Feedback;

import java.util.List;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
    String createEmailContent(List<Feedback> unreadMessages, String name);
}
