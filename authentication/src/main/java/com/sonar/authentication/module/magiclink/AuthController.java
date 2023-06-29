package com.sonar.authentication.module.magiclink;

import com.sonar.authentication.module.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/sendlink")
    public String sendMagicLink(@RequestBody EmailDto emailDto){

        String token = UUID.randomUUID().toString();
        String email = emailDto.getEmail();

        Client client = new Client(email,token);

        // Save the token with the user's email in the database
        clientRepository.save(client);

        // Send the magic link containing the token to the user's email
        return linkService.sendMagicLink(client);
    }

    @GetMapping("/verifyMagicLink")
    public String linkVerification(@RequestParam("token") String token){
        return linkService.verifyOtp(token);
    }
}
