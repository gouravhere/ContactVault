package com.contractvault.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.contractvault.entities.User;

@Service
public interface UserService {


	    User saveUser(User user);

	    Optional<User> getUserById(String id);

	    Optional<User> updateUser(User user);

	    void deleteUser(String id);

	    boolean isUserExist(String userId);

	    boolean isUserExistByEmail(String email);

	    List<User> getAllUsers();

	    	User getUserByEmail(String email);

	    // add more methods here related user service[logic]

	
}
