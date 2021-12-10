package com.example.music.user;

import com.example.music.config.JwtTokenUtil;
import com.example.music.dto.AccountCreateDto;
import com.example.music.dto.VerifyCodeDto;
import com.example.music.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService UserDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody AccountCreateDto accountCreateDto) throws Exception {
        try {
            Users user = userService.getUserByEmail(accountCreateDto.getEmail()).get();
            if (!user.isActive()) {
                user.setPassword(passwordEncoder.encode(accountCreateDto.getPassword()));
                user.setUsername(accountCreateDto.getUsername());
                userService.updateUser(user);
                userService.setEmail(user);
                return user.getId().toString();
            }
        } catch (Exception NoSuchElementException) {
            Users user = userService.createMember(accountCreateDto);
            return user.getId().toString();
        }
        return "-1";
    }

    @PostMapping("/login")
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody AccountCreateDto accountCreateDto) throws Exception {
        userService.authenticate(accountCreateDto.getEmail(), accountCreateDto.getPassword());
        Users user = userService.getUserByEmail(accountCreateDto.getEmail()).get();
        Long role = user.getRole();
        final UserDetails userDetails = UserDetailsService.loadUserByUsername(accountCreateDto.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, role));
    }

    @PostMapping("/change_password")
    public int changePassword(@RequestBody AccountCreateDto accountCreateDto) throws Exception {
        return userService.changePassword(accountCreateDto);
    }

    @PostMapping("/get_user")
    public String getUser(@RequestBody String email) throws Exception{

        return userService.getUser(email);
    }

    //verification

    @PostMapping("/verify")
    public int verifyCodeAction(@RequestBody VerifyCodeDto verifyCodeDto) throws Exception {
        return userService.verifyCode(verifyCodeDto);
    }

    @PostMapping("/resend_otp")
    public String resendOtp(@RequestBody AccountCreateDto accountCreateDto) throws Exception {
        Users user = userService.getUserByEmail(accountCreateDto.getEmail()).get();
        userService.setEmail(user);
        return user.getId().toString();
    }


}
