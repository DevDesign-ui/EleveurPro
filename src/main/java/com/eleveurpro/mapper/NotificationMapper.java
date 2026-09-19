package com.eleveurpro.mapper;

import com.eleveurpro.dto.NotificationResponse;
import com.eleveurpro.entity.Notification;

public class NotificationMapper {

    public static NotificationResponse toResponse(Notification entity) {
        if (entity == null) return null;
        return NotificationResponse.builder()
                .id(entity.getId())
                .titre(entity.getTitre())
                .message(entity.getMessage())
                .type(entity.getType())
                .lu(entity.getLu())
                .dateCreation(entity.getDateCreation())
                .build();
    }
}
