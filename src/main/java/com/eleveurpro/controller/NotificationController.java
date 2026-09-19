package com.eleveurpro.controller;

import com.eleveurpro.dto.NotificationResponse;
import com.eleveurpro.security.AuthUtil;
import com.eleveurpro.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "Gestion des notifications utilisateur")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les notifications de l'utilisateur courant")
    public ResponseEntity<List<NotificationResponse>> getAll() {
        return ResponseEntity.ok(notificationService.getAllForCurrentUser(AuthUtil.getCurrentUserEmail()));
    }

    @GetMapping("/unread")
    @Operation(summary = "Lister les notifications non lues")
    public ResponseEntity<List<NotificationResponse>> getUnread() {
        return ResponseEntity.ok(notificationService.getUnreadForCurrentUser(AuthUtil.getCurrentUserEmail()));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Marquer une notification comme lue")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Générer automatiquement les notifications (stock bas, vaccinations, mises bas, traitements)")
    public ResponseEntity<Void> generate() {
        notificationService.generateNotifications();
        return ResponseEntity.ok().build();
    }
}
