package com.sonar.authentication.module;

import com.sonar.authentication.module.dto.*;
import com.sonar.authentication.module.exception.AuthFailException;
import com.sonar.authentication.module.exception.CustomException;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private AuthService authService;


    public ResponseDto signUp(SignupDto signupDto) {
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            //we have user signed
            throw new CustomException("User already present");
        }
        //save, hash, create token
        String encryptedPassword = signupDto.getPassword();

        try{
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(signupDto.getName(),
                signupDto.getEmail(),
                encryptedPassword);
        userRepository.save(user);

        final AuthToken authToken = new AuthToken(user);
        authService.saveConfirmationToken(authToken);

        ResponseDto responseDto = new ResponseDto("success", "New user is created Successfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public ResponseDto signIn(SigninDto signinDto) {
        User user = userRepository.findByEmail(signinDto.getEmail());
        if(Objects.isNull(user)){
            throw new AuthFailException("User is not valid");
        }
        //hash password
        String encryptPassword = signinDto.getPassword();
        try{
            if(!user.getPassword().equals(hashPassword(encryptPassword))){
                throw new AuthFailException("Wrong Password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String msg = otpService.generateOtp(user);
        if(msg == "success")
            return new ResponseDto("success", "Ask user to enter the OTP send to registered email");
        else
            return new ResponseDto(HttpStatus.BAD_REQUEST.toString(), "Error in generating otp");
    }

    public ResponseEntity<Object> verifyOtp(OtpVerifyDto otpVerifyDto) {
        User user = userRepository.findByEmail(otpVerifyDto.getEmail());
        if(user.getOtp() == otpVerifyDto.getOtp()){
            AuthToken token = authService.getToken(user);
            int userId = user.getId();
            if(Objects.isNull(token)){
                throw new CustomException("Token is not present");
            }
            SigninResDto data =  new SigninResDto("success", token.getToken(), userId);
            return ResponseEntity.ok(data);
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("otp verification failed");
    }
}
