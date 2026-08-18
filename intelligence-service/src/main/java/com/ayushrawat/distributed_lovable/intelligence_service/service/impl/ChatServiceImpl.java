package com.ayushrawat.distributed_lovable.intelligence_service.service.impl;


import com.ayushrawat.distributed_lovable.common_lib.security.AuthUtils;
import com.ayushrawat.distributed_lovable.intelligence_service.entity.ChatMessage;
import com.ayushrawat.distributed_lovable.intelligence_service.entity.ChatSession;
import com.ayushrawat.distributed_lovable.intelligence_service.entity.ChatSessionId;
import com.ayushrawat.distributed_lovable.intelligence_service.mapper.ChatMapper;
import com.ayushrawat.distributed_lovable.intelligence_service.repository.ChatMessageRepository;
import com.ayushrawat.distributed_lovable.intelligence_service.repository.ChatSessionRepository;
import com.ayushrawat.distributed_lovable.intelligence_service.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.ayushrawat.distributed_lovable.intelligence_service.dto.chat.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final AuthUtils authUtils;
    private final ChatMapper chatMapper;


    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {
        Long userId = authUtils.getCurrentUserId();

        ChatSession chatSession = chatSessionRepository.getReferenceById(
                new ChatSessionId(projectId, userId)
        );

        List<ChatMessage> chatMessageList =
                chatMessageRepository.findByChatSession(chatSession);

        log.info("Messages found = {}", chatMessageList.size());

        List<ChatResponse> responses =
                chatMapper.fromListOfChatMessages(chatMessageList);

        log.info("Mapped responses = {}", responses);

        return responses;
    }
}
