package com.ayushrawat.distributed_lovable.intelligence_service.repository;

import com.ayushrawat.distributed_lovable.intelligence_service.entity.ChatSession;
import com.ayushrawat.distributed_lovable.intelligence_service.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}
