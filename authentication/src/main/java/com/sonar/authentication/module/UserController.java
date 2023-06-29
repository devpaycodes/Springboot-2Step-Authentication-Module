package com.sonar.authentication.module;

import com.sonar.authentication.module.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public ResponseDto signin(@RequestBody SigninDto signinDto){
        return userService.signIn(signinDto);
    }

    @PostMapping("/otp-verify")
    public ResponseEntity<Object> otpVerification(@RequestBody OtpVerifyDto otpVerifyDto){
        return userService.verifyOtp(otpVerifyDto);
    }


}
