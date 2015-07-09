package be.insaneprogramming.springboot.axon;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.EventFileResolver;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
@ConditionalOnClass(CommandBus.class)
public class AxonAutoConfiguration {
        @Bean
        @ConditionalOnMissingBean
        CommandBus commandBus() {
            CommandBus bus = new SimpleCommandBus();
            return bus;
        }

        @Bean
        @ConditionalOnMissingBean
        CommandGateway commandGateway() {
            CommandGateway gateway = new DefaultCommandGateway(commandBus());
            return gateway;
        }

        @Bean
        @ConditionalOnMissingBean
        EventBus eventBus() {
            EventBus bus =  new SimpleEventBus();
            return bus;
        }

        @Bean
        @ConditionalOnMissingBean
        EventStore eventStore() throws IOException {
            File tempDir = Files.createTempDirectory("axon_events").toFile();
            EventFileResolver resolver = new SimpleEventFileResolver(tempDir);
            EventStore store =  new FileSystemEventStore(resolver);
            return store;
        }

        @Bean
        AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
            AnnotationCommandHandlerBeanPostProcessor p =  new AnnotationCommandHandlerBeanPostProcessor();
            p.setCommandBus(commandBus());
            return p;
        }

        @Bean
        AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
            AnnotationEventListenerBeanPostProcessor p = new AnnotationEventListenerBeanPostProcessor();
            p.setEventBus(eventBus());
            return p;
        }

}
