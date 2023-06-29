package com.sonar.authentication.module.magiclink;

import com.sonar.authentication.module.UserRepository;
import com.sonar.authentication.module.UserService;
import com.sonar.authentication.module.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LinkService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    JavaMailSender javaMailSender;


    public String sendMagicLink(Client client){
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("devlabsystem@gmail.com");// input the senders email ID
            msg.setTo(client.getEmail());

            msg.setSubject("Channel to my welcome - PayInt Sonar");
            msg.setText("Click the following link to login: http://localhost:8090/verifyMagicLink?token=" + client.getToken());

            javaMailSender.send(msg);

            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    public String verifyOtp(String token) {
        Client client = clientRepository.findByToken(token);
        Date timeNow = new Date();
        Boolean valid = validateTime(timeNow,client.getCraetedat());
        if(valid){
            return userService.makeClientLogin(client);
        }else {
            return "Invalid magic link, please try again after sometime";
        }
    }

    public Boolean validateTime(Date date1, Date date2){
        long diffInMilli = Math.abs(date1.getTime() - date2.getTime());
        long thirtyMinsInMilli = 30 * 60 * 1000;
        return thirtyMinsInMilli > diffInMilli;
    }
}
