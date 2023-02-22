package com.security.example.demo;

import com.security.example.demo.util.ProjectConstants;
import com.twilio.Twilio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        Twilio.init(ProjectConstants.TWILIO_SID, ProjectConstants.TWILIO_TOKEN);
        SpringApplication.run(DemoApplication.class, args);
    }

}
