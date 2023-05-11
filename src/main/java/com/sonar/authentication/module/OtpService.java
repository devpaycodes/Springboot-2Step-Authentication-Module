package com.sonar.authentication.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;


    public String generateOtp(User user) {
        try {
            int randomPIN = (int) (Math.random() * 9000) + 1000;
            user.setOtp(randomPIN);
            userRepository.save(user);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("devlabsystem@gmail.com");// input the senders email ID
            msg.setTo(user.getEmail());

            msg.setSubject("Channel to my welcome - PayInt Sonar");
            msg.setText("Hello \n\n" +"Your Login OTP :" + randomPIN + ".Please Verify. \n\n"+"Regards \n"+"ABC");

            javaMailSender.send(msg);

            return "success";
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
