package com.example.music.user;

import com.example.music.dto.AccountCreateDto;
import com.example.music.dto.VerifyCodeDto;
import com.example.music.mail.Mail;
import com.example.music.mail.MailService;
import com.example.music.user.verfication.Verification;
import com.example.music.user.verfication.VerificationRepository;
import com.example.music.util.RandomUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final VerificationRepository verificationRepository;


    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, MailService mailService, VerificationRepository verificationRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.verificationRepository = verificationRepository;
    }

    public Users createMember(AccountCreateDto accountDto) throws MessagingException {
        String email = accountDto.getEmail();
        String username = accountDto.getUsername();
        String password = accountDto.getPassword();
        Users user = new Users();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(false);
        user.setRole(Long.parseLong("2"));
        setEmail(user);
        return userRepository.save(user);
    }

    public Users updateUser(Users user){
        return userRepository.save(user);
    }

    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public int changePassword(AccountCreateDto accountCreateDto) {
        Users user=getUserByEmail(accountCreateDto.getEmail()).get();
        user.setPassword(passwordEncoder.encode(accountCreateDto.getPassword()));
        updateUser(user);
        return 1;

    }

    public String getUser(String email) {
        try {
            Users user=getUserByEmail(email).get();
            if(user.isActive()) {
                setEmail(user);
                return user.getId().toString();
            }
            return "-1";
        }
        catch(Exception NoSuchElementException) {}
        return "-1";
    }

    //verification

    public void setEmail(Users user) throws MessagingException {
        String token = RandomUtil.generateRandomStringNumber(6).toUpperCase();
        Verification verification = new Verification();
        verification.setAccount(user);
        verification.setCreatedDate(LocalDateTime.now());
        verification.setExpiredDataToken(3);
        verification.setToken(token);
        verificationRepository.save(verification);
        Map<String, Object> maps = new HashMap<>();
        maps.put("account", user);
        maps.put("token", token);
        Mail mail = new Mail();
        mail.setFrom("care.info.music@gmail.com");
        mail.setSubject("Registration");
        mail.setTo(user.getEmail());
        mail.setModel(maps);
        mailService.sendEmail(mail);
    }

    public int verifyCode(VerifyCodeDto verifyCodeDto) {
        Verification verification = verificationRepository.findToken(verifyCodeDto.getId());
        if (verifyCodeDto.getToken().equals(verification.getToken())) {
            if (LocalDateTime.now().isBefore(verification.getExpiredDataToken())) {
                Users user = verification.getAccount();
                user.setActive(true);
                updateUser(user);
                return 11;
            }
            return 8;
        } else {
            return 12;
        }
    }

}
