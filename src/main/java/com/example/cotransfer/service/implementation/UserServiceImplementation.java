package com.example.cotransfer.service.implementation;

import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.TransferUser;
import com.example.cotransfer.model.User;
import com.example.cotransfer.repository.TransferRepository;
import com.example.cotransfer.repository.TransferUserRepository;
import com.example.cotransfer.repository.UserRepository;
import com.example.cotransfer.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    private final TransferRepository transferRepository;

    private final TransferUserRepository transferUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        log.info("Получение всех пользователей");
        List<User> allUsers = userRepository.findAll();
        log.info("Все пользователи получены");
        return allUsers;
    }

    @Override
    public User getUser(Long id) {
        log.info("Получение пользователя c token");
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            log.info("Получен пользователь");
            return user;
        } else throw new EntityNotFoundException("Пользователь не найден");
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Попытка получения пользователя по email: " + email);
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден: " + email));
        log.info("Пользователь получен: " + user.getId());
        return user;
    }

    @Override
    public void save(User user) {
        log.info("Создание пользователя");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("Пользователь создан");
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Удаление пользователя c id {}", id);
        userRepository.deleteById(id);
        log.info("Пользователь c id {} удален", id);
    }

    @Override
    public void update(User user) {
        log.info("Обновление пользователя с id {}", user.getId());
        userRepository.save(user);
        log.info("Пользователь с id {} обновлен", user.getId());
    }

    @Override
    public void createUser(Long transferId, String user) {
        log.info("Создание пользователя");
        JSONObject jsonObject = new JSONObject(user);
        Optional<Transfer> transfer = transferRepository.findById(transferId);
        User newUser = new User();
        String name = jsonObject.getString("FCs");
        List<String> nameList = List.of(name.split(" "));
        newUser.setName(nameList.get(0));
        newUser.setArrivalDate(jsonObject.getString("arrivalDate"));
        newUser.setFlightNumber(jsonObject.getString("flightNumber"));
        newUser.setPhoneNumber(jsonObject.getString("phoneNumber"));
        newUser.setEmail(jsonObject.getString("email"));
        newUser.setTelegramLogin(jsonObject.getString("telegramLogin"));
        newUser.setTripComment(jsonObject.getString("tripComment"));
        newUser.setTransfer((List<Transfer>) transfer.get());
        userRepository.save(newUser);
        log.info("Пользователь создан");
    }

    @Override
    public Set<Transfer> getAllUserTransfers(Long id) {
        log.info("Получение всех поездок пользователя с id {}:", id);
        List<TransferUser> allTransferUser = transferUserRepository.findAllByUserIdentificationNumber(id);
        Set<Transfer> allTransfers = new HashSet<>();
        for (TransferUser tempTransferUser : allTransferUser) {
            allTransfers.add(tempTransferUser.getTransferId());
        }
        
        for (Transfer newTransfer : allTransfers) {
            List<TransferUser> transferUser = new ArrayList<>();
            List<User> userList = new ArrayList<>();
            transferUser = transferUserRepository.findAllByTransferId(newTransfer);
            for (TransferUser newTrans: transferUser) {
                userList.add(newTrans.getUserId());
            }
            newTransfer.setUsers(userList);
        }
        log.info("Все поездки пользователя с id {} " + "получены", id);

        return allTransfers;

    }


}
