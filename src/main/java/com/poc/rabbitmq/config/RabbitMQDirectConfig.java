package com.poc.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQDirectConfig {

    public static final String EXCHANGE_NAME = "direct-exchange";
    public static final String INCOMING_QUEUE_NAME = "incoming.queue";
    public static final String DEAD_LETTER_QUEUE_NAME = "dead-letter.queue";

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue incomingQueue() {
        return QueueBuilder.durable(INCOMING_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_NAME)
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_NAME).build();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue marketingQueue() {
        return new Queue("marketingQueue", false);
    }

    @Bean
    Queue financeQueue() {
        return new Queue("financeQueue", false);
    }

    @Bean
    Queue adminQueue() {
        return new Queue("adminQueue", false);
    }



    @Bean
    Binding marketingBinding(Queue marketingQueue, DirectExchange exchange) {
        return BindingBuilder.bind(marketingQueue).to(exchange).with("marketing");
    }

    @Bean
    Binding financeBinding(Queue financeQueue, DirectExchange exchange) {
        return BindingBuilder.bind(financeQueue).to(exchange).with("finance");
    }

    @Bean
    Binding adminBinding(Queue adminQueue, DirectExchange exchange) {
        return BindingBuilder.bind(adminQueue).to(exchange).with("admin");
    }


}
