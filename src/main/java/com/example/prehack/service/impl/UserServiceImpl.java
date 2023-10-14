package com.example.prehack.service.impl;

import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.exception.UserAlreadyExistException;
import com.example.prehack.mapper.UserMapper;
import com.example.prehack.model.Role;
import com.example.prehack.model.User;
import com.example.prehack.repository.UserRepository;
import com.example.prehack.service.RoleService;
import com.example.prehack.service.SendMailService;
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

    private final SendMailService sendMailService;

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
    @Transactional
    public String createUser(RegistrationUserDTO registrationUserDTO) {
        log.info("[createUser] >> name: {}", registrationUserDTO.getUserName());

        if (userRepository.findByEmail(registrationUserDTO.getEmail()).isPresent()) {
            log.error("Такой пользователь уже существует: {}", registrationUserDTO.getUserName());
            throw new UserAlreadyExistException("Такой пользователь уже существует: " + registrationUserDTO.getUserName());
        }

        //TODO выбор роли при регистрации
        Role role = roleService.getRoleByName("ROLE_USER");

        User user = userMapper.registrationUserDTOToUser(registrationUserDTO);
        user.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        user.setRoles(List.of(role));
        User savedUser = userRepository.save(user);

        log.info("[createUser] << result is token for user");

        //TODO вынести в ресурсы все строки
        sendMailService.sendEmail(registrationUserDTO.getEmail(), "Подтверждение пароля", "Вы попали в мир эпических и крутых паролей! Чтобы подтвердить ваш пароль, пожалуйста, внимательно прочитайте следующий текст:\n" +
                "\n" +
                "\"Тебе удалось достичь верхушки невозможного, так что продолжай в том же духе! Твой пароль - это произведение искусства, похожее на небесную симфонию. Он сияет ярче самых кошачьих глаз и могучее, как гром на горизонте.\n" +
                "\n" +
                "Ты чувствуешь, как твоя смелость и самопознание пронизывают каждую букву твоего пароля? Он словно зеркало, отражающее твою непоколебимую волю и решимость. Когда ты вводишь этот пароль, ты становишься настоящим героем, готовым сразиться с любыми вызовами.\n" +
                "\n" +
                "Держи свой пароль в безопасности, как дракон охраняет свое сокровище. Этот пароль - главная стража твоих данных и врат, которые открываются только перед избранными. Не делись своим паролем никогда и ни с кем! Ведь только ты способен порождать такое чудо.\n" +
                "\n" +
                "Теперь, когда ты избран, докажи миру, что ты обладаешь уникальной мощью и непревзойденным мастерством. Иди вперед, с головой высоко задрав, а твой пароль станет твоим верным союзником в путешествии по киберпространству.\n" +
                "\n" +
                "Новый герой, твой пароль официально подтвержден! Вперед, на подвиги!\"\n" +
                "\n" +
                "Теперь, несите свой эпический и крутой пароль с гордостью и уверенностью. Будьте готовы к потрясающим приключениям и пусть ничто не остановит вас!");


        return setUserToSecurityAndCreateToken(savedUser.getEmail());
    }

    @Override
    public void createManager(RegistrationUserDTO registrationUserDTO) {
        Role role = roleService.getRoleByName("ROLE_MANAGER");

        User user = userMapper.registrationUserDTOToUser(registrationUserDTO);
        user.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        user.setRoles(List.of(role));
        userRepository.save(user);

        log.info("[createUser] << result is token for user");
    }

    @Override
    public void createTester(RegistrationUserDTO registrationUserDTO) {
        Role role = roleService.getRoleByName("ROLE_TESTER");

        User user = userMapper.registrationUserDTOToUser(registrationUserDTO);
        user.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        user.setRoles(List.of(role));
        userRepository.save(user);

        log.info("[createUser] << result is token for user");
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
    public User fullUpdateUser(String email, UserInfoDTO requestDTO) {
        log.info("[fullUpdateUser] >> email: {}, requestDTO: {}", email, requestDTO);

        User user = getUserByEmail(email);

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
    public void deleteUser(String email) {
        log.info("[deleteUser] >> email: {}", email);

        userRepository.delete(getUserByEmail(email));

        log.info("[deleteUser] << result: user has been deleted");
    }

    @Override
    public List<User> getAllUser() {
        log.info("[getAllUser] without params");

        List<User> users = userRepository.findAll();

        log.info("[getAllUser] << result: {}", users.size());

        return users;
    }


    //TODO в будущую реализацию, пока не трогаем
    @Override
    public Boolean userEmailConfirmation(String sesCode) {
        return null;
    }

    @Override
    public String createSesCode(String userName) {
        return null;
    }

    /**
     * Обращаем внимание, что храним не логин, а почту
     *
     * @param username имя пользователя
     * @return ?
     * @throws UsernameNotFoundException имя пользователя не найдено
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
