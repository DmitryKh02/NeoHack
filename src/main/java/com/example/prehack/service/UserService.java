package com.example.prehack.service;

import com.example.prehack.model.User;
import com.example.prehack.web.dto.RegistrationUserDTO;
import com.example.prehack.web.dto.UserInfoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    /**
     * Retrieve a user from the database by their email.
     *
     * @param email The email of the user to find.
     * @return The user object associated with the given email.
     */
    User getUserByEmail(String email);

    /**
     * Retrieve a user from the database by their name.
     *
     * @param name The name of the user to find.
     * @return The user object associated with the given name.
     */
    User getUserByName(String name);

    /**
     * Retrieve a user from the database by their ID.
     *
     * @param userId The unique identifier of the user.
     * @return The user object associated with the given ID.
     */
    User getUserById(Long userId);

    /**
     * Register a new user and save their information in the database.
     *
     * @param registrationUserDTO Information required for user registration.
     * @return A JWS (JSON Web Signature) token for the registered user.
     */
    String createUser(RegistrationUserDTO registrationUserDTO);


    /**
     /**
     * Create a user with the role "USER."
     *
     * @param registrationUserDTO Information about the user.
     * @param admin with user created by admin
     */
    void createUser(RegistrationUserDTO registrationUserDTO, Boolean admin);
    /**
     * Create a user with the role "MANAGER."
     *
     * @param registrationUserDTO Information about the user.
     */
    void createManager(RegistrationUserDTO registrationUserDTO);

    /**
     * Create a user with the role "TESTER."
     *
     * @param registrationUserDTO Information about the user.
     */
    void createTester(RegistrationUserDTO registrationUserDTO);

    /**
     * Create a JWS token for a user.
     *
     * @param name The name of the user.
     * @return The JWS token in string format.
     */
    @Transactional
    String createTokenForUser(String name);

    /**
     * NOT IMPLEMENTED | Check a session code for correctness.
     *
     * @param sesCode The session code to check.
     * @return true if the session code is correct, false if it's incorrect.
     */
    Boolean userEmailConfirmation(String sesCode);

    /**
     * NOT IMPLEMENTED | Generate a session code for confirming a user's email.
     *
     * @param name The name of the user.
     * @return The session code in string format.
     */
    String createSesCode(String name);

    /**
     * Update user information in the database.
     *
     * @param email The email of the user to update.
     * @param requestDTO Information for updating the user's profile.
     * @return The updated user object.
     */
    User fullUpdateUser(String email, UserInfoDTO requestDTO);

    /**
     * Update some information about a user.
     *
     * @param name The name of the user to update.
     * @param requestDTO Information for updating the user's profile.
     * @return The updated user object.
     */
    User updateSomeUserInfo(String name, UserInfoDTO requestDTO);

    /**
     * Delete a user from the database based on their email.
     *
     * @param email The email of the user to be deleted.
     */
    void deleteUser(String email);

    /**
     * Method for Admin | Retrieve a list of all users from the database.
     *
     * @return A list of all user objects in the database.
     */
    List<User> getAllUser();

}
