package com.example.prehack.service.impl;

import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.exception.UserAlreadyExistException;
import com.example.prehack.mapper.UserMapper;
import com.example.prehack.model.User;
import com.example.prehack.repository.UserRepository;
import com.example.prehack.service.RoleService;
import com.example.prehack.service.UserService;
import com.example.prehack.utils.JwtTokenUtils;
import com.example.prehack.web.dto.RegistrationUserDTO;
import com.example.prehack.web.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final JwtTokenUtils jwtTokenUtils;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final UserMapper userMapper;

    @Override
    public User getUserByEmail(String email) {
        log.info("[getUserByEmail] >> email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found by this email :{} ", email);
                    return new ResourceNotFoundException("User not found by this email :: " + email);
                });

        log.info("[getUserByEmail] << result: {}", user.getUserName());

        return user;
    }

    @Override
    public User getUserByName(String name) {

        log.info("[getUserByName] >> name: {}", name);

        User user = userRepository.findByUserName(name)
                .orElseThrow(() -> {
                    log.error("User not found by this name :{} ", name);
                    return new UsernameNotFoundException("User not found by this name :: " + name);
                });

        log.info("[getUserByName] << result: {}", user.getUserName());

        return user;
    }

    @Override
    public User getUserById(Long userId) {
        log.info("[getUserById] >> userId: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found by this id :{} ", userId);
                    return new ResourceNotFoundException("User not found by this id :: " + userId);
                });

        log.info("[getUserById] << result: {}", user.getUserName());

        return user;
    }

    @Override
    public String createUser(RegistrationUserDTO registrationUserDTO) {
        log.info("[createUser] >> name: {}", registrationUserDTO.getUserName());

        if (userRepository.findByEmail(registrationUserDTO.getEmail()).isPresent()) {
            log.error("Такой пользователь уже существует: {}", registrationUserDTO.getUserName());
            throw new UserAlreadyExistException("Такой пользователь уже существует: " + registrationUserDTO.getUserName());
        }

        //TOdo выбор роли при регистрации
        User user = userMapper.registrationUserDTOToUser(registrationUserDTO);
        user.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        user.setRoles(List.of(roleService.getRoleByName("ROLE_USER")));

        User savedUser = userRepository.save(user);

        log.info("[createUser] << result is token for user");

        return setUserToSecurityAndCreateToken(savedUser.getUserName());
    }

    @Transactional
    @Override
    public String setUserToSecurityAndCreateToken(String email) {
        log.info("[setUserToSecurityAndCreateToken] >> create token for email: {}", email);

        UserDetails userDetails = loadUserByUsername(email);

        log.info("[setUserToSecurityAndCreateToken] << result is token");

        return jwtTokenUtils.generateToken(userDetails);
    }

    @Override
    public User fullUpdateUser(String name, UserInfoDTO requestDTO) {
        log.info("[fullUpdateUser] >> name: {}, requestDTO: {}", name, requestDTO);

        User user = getUserByName(name);

        User userWithNewInfo = userMapper.UserInfoDTOToUserFull(requestDTO);
        userWithNewInfo.setUserId(user.getUserId());

        User savedUser = userRepository.save(userWithNewInfo);

        log.info("[fullUpdateUser] << result: {}", savedUser);

        return savedUser;
    }

    @Override
    public User updateSomeUserInfo(String name, UserInfoDTO requestDTO) {
        log.info("[updateSomeUserInfo] >> name: {}, requestDTO: {}", name, requestDTO);

        User user = getUserByName(name);

        User userWithNewInfo = userMapper.UserInfoDTOToUserFull(requestDTO);
        userWithNewInfo.setUserId(user.getUserId());

        User savedUser = userRepository.save(userWithNewInfo);

        log.info("[updateSomeUserInfo] << result: {}", savedUser);

        return savedUser;
    }

    @Override
    public void deleteUser(String userName) {
        log.info("[deleteUser] >> name: {}", userName);

        userRepository.delete(getUserByName(userName));

        log.info("[deleteUser] << result: user has been deleted");
    }

    @Override
    public List<User> getAllUser() {
        log.info("[getAllUser] without params");

        List<User> users = userRepository.findAll();

        log.info("[getAllUser] << result: {}", users.size());

        return users;
    }

    @Override
    public Boolean userEmailConfirmation(String sesCode) {
        return null;
    }

    @Override
    public String createSesCode(String userName) {
        return null;
    }

    /**
     * Образаем пвнимание что храним не логин а почту
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByEmail(username);

        //Даём имя, пароль и мапим роль в спринговую
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));

    }
}
