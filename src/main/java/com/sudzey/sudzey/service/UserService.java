package com.sudzey.sudzey.service;

import com.sudzey.sudzey.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    String getUserIdtoUserName(String userName);
}
