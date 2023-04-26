package com.example.cotransfer.service.implementation;

import com.example.cotransfer.model.Transfer;
import com.example.cotransfer.model.TransferUser;
import com.example.cotransfer.model.User;
import com.example.cotransfer.repository.TransferRepository;
import com.example.cotransfer.repository.TransferUserRepository;
import com.example.cotransfer.repository.UserRepository;
import com.example.cotransfer.security.CustomUserDetails;
import com.example.cotransfer.service.TransferService;

import com.example.cotransfer.telegram.CoTransferBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImplementation implements TransferService {

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final TransferUserRepository transferUserRepository;


    @Override
    public ResponseEntity<?> deleteTransfer(String transfer) {
        JSONObject jsonObjectRequest = new JSONObject(transfer);
        JSONObject jsonObjectUpdate = jsonObjectRequest.getJSONObject(("order"));
        Long id = jsonObjectUpdate.getLong("id");
        log.info("Удаление трансфера с id: {}", id);
        Optional<Transfer> transferOptional = transferRepository.findById(id);
        Transfer newTransfer = transferOptional.get();
        List<TransferUser> transferUser = transferUserRepository.findAllByTransferId(newTransfer);
        transferUserRepository.deleteAll(transferUser);
        JSONArray jsonArray = jsonObjectUpdate.getJSONArray("users");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject arrayJson = jsonArray.getJSONObject(i);
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = customUserDetails.getUser();
            Long userId = arrayJson.getLong("id");
            Optional<User> userOptional = userRepository.findById(userId);
            User updateUser = userOptional.get();
            if (!user.getId().equals(userId)){
                userRepository.delete(updateUser);
            } else {
                updateUser.setArrivalDate(null);
                updateUser.setArrivalTime(null);
                updateUser.setFlightNumber(null);
                updateUser.setName(null);
                updateUser.setPassport(null);
                updateUser.setPhoneNumber(null);
                updateUser.setTripComment(null);
                userRepository.save(updateUser);
            }

        }
        transferRepository.delete(newTransfer);
        log.info("Трансфер удалён с id: {}", id);
        return ResponseEntity.ok(id);
    }


    public ResponseEntity<?> createTransferFromAirport(String transfer) {
        log.info("Создание трансфера");
        Transfer newTransfer = new Transfer();
        JSONObject jsonObjectTr = new JSONObject(transfer);
        JSONObject jsonObject = jsonObjectTr.getJSONObject(("order"));
        List<User> userList = new ArrayList<>();
        newTransfer.setTransferDate(jsonObject.getString("transferDate"));
        newTransfer.setTransferTime(jsonObject.getString("transferTime"));
        newTransfer.setStartLocation(jsonObject.getString("startLocation"));
        newTransfer.setIsPickUpFromAirport(jsonObject.getBoolean("isPickUpFromAirport"));
        newTransfer.setAdultsAmount(jsonObject.getInt("adultsAmount"));
        newTransfer.setEndLocation(jsonObject.getString("endLocation"));
        newTransfer.setCarType(jsonObject.getString("carType"));
        newTransfer.setAdultsAmount(jsonObject.getInt("adultsAmount"));
        newTransfer.setChildrenUnder5(jsonObject.getInt("childrenUnder5"));
        newTransfer.setChildrenAbove5(jsonObject.getInt("childrenAbove5"));
        newTransfer.setIsEnded(false);
        newTransfer.setIsShared(jsonObject.getBoolean("isShared"));
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (int i = 0; i < jsonArray.length(); i++) {
            TransferUser transferUser = new TransferUser();
            JSONObject arrayJson = jsonArray.getJSONObject(i);
            Optional<User> user = userRepository.findUserByEmail(arrayJson.getString("email"));
            User newUser = user.orElseGet(User::new);
            newUser.setArrivalDate(arrayJson.getString("arrivalDate"));
            newUser.setArrivalTime(arrayJson.getString("arrivalTime"));
            newUser.setEmail(arrayJson.getString("email"));
            newUser.setFlightNumber(arrayJson.getString("flightNumber"));
            newUser.setName(arrayJson.getString("name"));
            newUser.setPassport(arrayJson.getString("passport"));
            newUser.setPhoneNumber(String.valueOf(arrayJson.getString("phoneNumber")));
            newUser.setTripComment(arrayJson.getString("tripComment"));
            userList.add(newUser);
            transferUser.setTransferId(newTransfer);
            transferUser.setUserId(newUser);
            newTransfer.setUsers(userList);
            transferRepository.save(newTransfer);
            transferUserRepository.save(transferUser);
            log.info("Пользователь создан");
        }
        log.info("Трансфер создан");
        return ResponseEntity.ok(newTransfer);
    }

    @Override
    public List<Transfer> getAllTransfers() {
        log.info("Получение всех трансферов");
        List<Transfer> allTransfers = transferRepository.findAllByIsShared(false);
        log.info("Все трансферы получены");
        return allTransfers;
    }

    @Override
    public ResponseEntity<?> updateTransfer(String transfer) {
        JSONObject jsonObjectRequest = new JSONObject(transfer);
        JSONObject jsonObjectUpdate = jsonObjectRequest.getJSONObject(("order"));
        List<User> userList = new ArrayList<>();
        Long id = jsonObjectUpdate.getLong("id");
        log.info("Обновление трансфера с id:{}", id);
        System.out.println(id);
        Optional<Transfer> transferOptional = transferRepository.findById(id);
        Transfer newTransfer = transferOptional.get();
        newTransfer.setTransferDate(jsonObjectUpdate.getString("transferDate"));
        newTransfer.setTransferTime(jsonObjectUpdate.getString("transferTime"));
        newTransfer.setStartLocation(jsonObjectUpdate.getString("startLocation"));
        newTransfer.setIsPickUpFromAirport(jsonObjectUpdate.getBoolean("isPickUpFromAirport"));
        newTransfer.setAdultsAmount(jsonObjectUpdate.getInt("adultsAmount"));
        newTransfer.setEndLocation(jsonObjectUpdate.getString("endLocation"));
        newTransfer.setCarType(jsonObjectUpdate.getString("carType"));
        newTransfer.setAdultsAmount(jsonObjectUpdate.getInt("adultsAmount"));
        newTransfer.setChildrenUnder5(jsonObjectUpdate.getInt("childrenUnder5"));
        newTransfer.setChildrenAbove5(jsonObjectUpdate.getInt("childrenAbove5"));
        newTransfer.setIsShared(jsonObjectUpdate.getBoolean("isShared"));
        JSONArray jsonArray = jsonObjectUpdate.getJSONArray("users");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject arrayJson = jsonArray.getJSONObject(i);
            Optional<User> userOptional = userRepository.findUserByEmail(arrayJson.getString("email"));
            if (userOptional.isPresent()) {
                User updateUser = userOptional.get();
                updateUser.setArrivalDate(arrayJson.getString("arrivalDate"));
                updateUser.setArrivalTime(arrayJson.getString("arrivalTime"));
                updateUser.setEmail(arrayJson.getString("email"));
                updateUser.setFlightNumber(arrayJson.getString("flightNumber"));
                updateUser.setName(arrayJson.getString("name"));
                updateUser.setPassport(arrayJson.getString("passport"));
                updateUser.setPhoneNumber(String.valueOf(arrayJson.getString("phoneNumber")));
                updateUser.setTripComment(arrayJson.getString("tripComment"));
                userList.add(updateUser);
                userRepository.save(updateUser);
            } else {
                User updateUser = new User();
                TransferUser transferUser = new TransferUser();
                updateUser.setArrivalDate(arrayJson.getString("arrivalDate"));
                updateUser.setArrivalTime(arrayJson.getString("arrivalTime"));
                updateUser.setEmail(arrayJson.getString("email"));
                updateUser.setFlightNumber(arrayJson.getString("flightNumber"));
                updateUser.setName(arrayJson.getString("name"));
                updateUser.setPassport(arrayJson.getString("passport"));
                updateUser.setPhoneNumber(String.valueOf(arrayJson.getString("phoneNumber")));
                updateUser.setTelegramLogin(arrayJson.getString("telegramLogin"));
                updateUser.setTripComment(arrayJson.getString("tripComment"));
                transferUser.setTransferId(newTransfer);
                transferUser.setUserId(updateUser);
                userList.add(updateUser);
                userRepository.save(updateUser);
                transferUserRepository.save(transferUser);
            }
        }

        newTransfer.setUsers(userList);
        transferRepository.save(newTransfer);
        log.info("Данные обновлены");
        return ResponseEntity.ok(newTransfer);
    }

    @Override
    public Transfer getTransfer(Long id) {
        log.info("Попытка получение трансфера с id = {}",id);
        Optional<Transfer> optional = transferRepository.findById(id);
        if (optional.isPresent()){
            log.info("Успешная попытка получение трансфера с id = {}",id);
            return optional.get();
        } else {
            log.info("трансфера с id = {} не существует",id);
        }
        return null;
    }
}
