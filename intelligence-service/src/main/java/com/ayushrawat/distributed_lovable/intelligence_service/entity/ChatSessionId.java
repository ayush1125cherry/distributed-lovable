package com.ayushrawat.distributed_lovable.intelligence_service.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ChatSessionId implements Serializable {
    Long projectId;
    Long userId;
}
