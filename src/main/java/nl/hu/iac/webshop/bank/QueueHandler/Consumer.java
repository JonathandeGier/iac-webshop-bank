package nl.hu.iac.webshop.bank.QueueHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.iac.webshop.bank.DTO.BankConfirmationDTO;
import nl.hu.iac.webshop.bank.services.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

    @Autowired
    private ConsumerService consumerService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = "acknowledgeToBank")
    public void receive(String message) {
        logger.info("received message from webshop - {}", message);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BankConfirmationDTO confirmation = new BankConfirmationDTO(jsonNode.get("bestellingId").asLong(), jsonNode.get("accept").asBoolean());

        consumerService.acknowledge(confirmation);

    }
}
