package com.eleveurpro.repository;

import com.eleveurpro.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByDateCreationDesc(Long userId);
    List<Notification> findByUserIdAndLuFalseOrderByDateCreationDesc(Long userId);
}
