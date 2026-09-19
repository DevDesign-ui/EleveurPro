package com.eleveurpro.service;

import com.eleveurpro.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getAllForCurrentUser(String email);
    List<NotificationResponse> getUnreadForCurrentUser(String email);
    NotificationResponse markAsRead(Long id);
    void generateNotifications();
}
