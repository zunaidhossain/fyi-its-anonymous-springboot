package com.zunaidhossain.fyi_its_anonymous.Service;

import com.zunaidhossain.fyi_its_anonymous.Scheduler.UnreadMailScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    UnreadMailScheduler unreadMailScheduler;

    @Test
    void sendMail() {
        unreadMailScheduler.sendUnreadMessageEmails();
    }
}
