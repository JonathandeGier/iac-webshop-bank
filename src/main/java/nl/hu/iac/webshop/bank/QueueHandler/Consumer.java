package nl.hu.iac.webshop.bank.QueueHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.iac.webshop.bank.DTO.BankConfirmationDTO;
import nl.hu.iac.webshop.bank.services.ConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

    @Autowired
    private ConsumerService consumerService;

    @RabbitListener(queues = "acknowledgeToBank")
    public void receive(String message) {

        BankConfirmationDTO confirmation = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            confirmation = mapper.readValue(message, BankConfirmationDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        consumerService.acknowledge(confirmation);

    }
}
