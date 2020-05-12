package com.hms.consumerclient.configuration;

import com.hms.consumerclient.service.RabbitMessageHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.hms.consumerclient.constants.RabbitProperties.EXCHANGE_NAME;
import static com.hms.consumerclient.constants.RabbitProperties.QUEUE_NAME;

@Configuration
public class RabbitConfig {

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("hms.#");
    }

    @Bean
    SimpleMessageListenerContainer listenerContainer(ConnectionFactory factory, MessageListenerAdapter adapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(adapter);
        return container;
    }

    @Bean
    MessageListenerAdapter adapter(RabbitMessageHandler handler) {
        return new MessageListenerAdapter(handler, "handler");
    }
}
