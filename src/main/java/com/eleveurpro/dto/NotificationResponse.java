package com.eleveurpro.dto;

import com.eleveurpro.entity.enums.TypeNotification;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private String titre;
    private String message;
    private TypeNotification type;
    private Boolean lu;
    private LocalDateTime dateCreation;
}
