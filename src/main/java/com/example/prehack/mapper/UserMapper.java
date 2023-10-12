package com.example.prehack.mapper;

import com.example.prehack.model.User;
import com.example.prehack.web.dto.RegistrationUserDTO;
import com.example.prehack.web.dto.UserInfoDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    User registrationUserDTOToUser(RegistrationUserDTO requestDTO);

    User UserInfoDTOToUser(UserInfoDTO requestDTO, @MappingTarget User user);

    User UserInfoDTOToUserFull(UserInfoDTO requestDTO);
}
