package com.ayushrawat.distributed_lovable.common_lib.error;

import org.springframework.context.annotation.Bean;

public class SharedExceptionAutoConfiguration {
    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}
