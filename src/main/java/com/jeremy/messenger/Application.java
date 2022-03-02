package com.jeremy.messenger;

import com.jeremy.messenger.views.messenger.ChatMessage;
import com.vaadin.flow.component.dependency.NpmPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    Sinks.Many<ChatMessage> publisher() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    Flux<ChatMessage> messages(Sinks.Many<ChatMessage> publisher) {
        return publisher.asFlux();
    }
}
