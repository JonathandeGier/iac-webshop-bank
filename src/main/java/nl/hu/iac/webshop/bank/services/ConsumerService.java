package nl.hu.iac.webshop.bank.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.iac.webshop.bank.DTO.BankConfirmationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Exchange exchange;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void acknowledge(BankConfirmationDTO confirmation) {

        // aanvraag bevestigen
        confirmation.setAccept(!confirmation.isAccept());

        // stuur bericht terug naar de webshop via een Queue
        String routingKey = "bank.response";
        String json = convertToJson(confirmation);
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, json);
        logger.info("send message back to webshop - {}", json);
    }

    private String convertToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
