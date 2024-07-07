package org.example.service;

import org.example.dto.payment.request.UserInfoUpdateRequest;
import org.example.dto.payment.response.user.UserInfoResponse;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserInfoResponse getUserInfoById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(EntityNotFoundException::new);
        UserInfoResponse resp = new UserInfoResponse(user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPhone());
        return resp;
    }

    public UserInfoResponse updateUserInfo(UserInfoUpdateRequest req, Long userId) {
        User user = userRepository.findUserById(userId).orElseThrow(EntityNotFoundException::new);
        if (req.getUsername() != null) {
            user.setUsername(req.getUsername());
        }
        if (req.getFirstname() != null) {
            user.setFirstname(req.getFirstname());
        }
        if (req.getLastname() != null) {
            user.setLastname(req.getLastname());
        }
        if (req.getEmail() != null) {
            user.setEmail(req.getEmail());
        }
        if (req.getPhone() != null) {
            user.setPhone(req.getPhone());
        }

        userRepository.saveAndFlush(user);
        return getUserInfoById(userId);
    }


}
