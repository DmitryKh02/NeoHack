package com.example.prehack.service;

import com.example.prehack.model.User;
import com.example.prehack.web.dto.RegistrationUserDTO;
import com.example.prehack.web.dto.UserInfoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    /**
     * Selected user from database by email
     *
     * @param email user for find
     * @return user from database
     */
    User getUserByEmail(String email);

    User getUserByName(String name);

    /**
     * Selected user from database by id
     *
     * @param userId user for find
     * @return user from database
     */
    User getUserById(Long userId);

    /**
     * Registration user and save him in database
     *
     * @param registrationUserDTO user information for registration
     * @return jws token for user
     */
    String createUser(RegistrationUserDTO registrationUserDTO);

    @Transactional
    String setUserToSecurityAndCreateToken(String name);

    /**
     * NOT IMPLEMENTED | Check sesCode to correct
     *
     * @param sesCode for checking
     * @return true - sesCode is corrected | false - sesCode uncorrected
     */
    Boolean userEmailConfirmation(String sesCode);

    /**
     * NOT IMPLEMENTED | Generated ses code for confirmation user Email
     *
     * @param userName
     * @return
     */
    String createSesCode(String userName);

    /**
     * Update information about user
     *
     * @param name       - user name for update
     * @param requestDTO - missing information about client
     * @return updated client
     */
    User fullUpdateUser(String name, UserInfoDTO requestDTO);

    /**
     * Update some information about user
     *
     * @param name       - user name for update
     * @param requestDTO - missing information about client
     * @return updated client
     */
    User updateSomeUserInfo(String name, UserInfoDTO requestDTO);

    /**
     * Delete user from database
     *
     * @param userName for deleting
     */
    void deleteUser(String userName);

    /**
     * Method for Admin | Select all user from database
     *
     * @return all user
     */
    List<User> getAllUser();


}
