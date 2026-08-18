package com.ayushrawat.distributed_lovable.intelligence_service.service;



import com.ayushrawat.distributed_lovable.intelligence_service.dto.chat.StreamResponse;
import reactor.core.publisher.Flux;


public interface AIGenerationService {
    Flux<StreamResponse> streamResponse(String message, Long aLong);
}
