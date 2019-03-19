package nl.hu.iac.webshop.bank.config;

import nl.hu.iac.webshop.bank.QueueHandler.Consumer;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    public Exchange eventExchange() {
        return new TopicExchange("bankConfirmation");
    }

    @Bean
    public Queue queue() {
        return new Queue("bankConfirmationQueue");
    }

    @Bean
    public Binding binding(Queue queue, Exchange eventExchange) {
        return BindingBuilder
                .bind(queue)
                .to(eventExchange)
                .with("bank.response")
                .noargs();
    }

    // Consumer schrijven we zo en hoeft dus niet geïmporteerd te worden
    @Bean
    public Consumer eventReceiver() {
        return new Consumer();
    }
}
