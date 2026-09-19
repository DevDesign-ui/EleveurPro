package com.eleveurpro.mapper;

import com.eleveurpro.dto.UserResponse;
import com.eleveurpro.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .role(user.getRole())
                .actif(user.getActif())
                .build();
    }
}
