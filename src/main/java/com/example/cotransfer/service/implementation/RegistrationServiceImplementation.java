package com.example.cotransfer.service.implementation;

import com.example.cotransfer.enums.Role;
import com.example.cotransfer.model.User;
import com.example.cotransfer.service.EmailService;
import com.example.cotransfer.service.RegistrationService;
import com.example.cotransfer.service.UserService;
import com.example.cotransfer.util.PassayPasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImplementation implements RegistrationService {

    private final UserService userService;
    private final PassayPasswordGenerator passayPasswordGenerator;
    private final EmailService emailService;

    @Override
    @Transactional
    public void register(String email) {
        log.info("Пришел запрос на регистрацию пользователя: " + email);
        String password = passayPasswordGenerator.generatePassayPassword();
        User user = User.builder()
                .email(email)
                .password(password)
                .role(Role.USER)
                .build();
        userService.save(user);
        emailService.sendEmail(email,
                "Дорогой наш супер хороший клиент",
                email + " \nВременный пароль: " + password);
        log.info("Пользователь зарегистрирован, email отправлен: " + email);
    }
}
