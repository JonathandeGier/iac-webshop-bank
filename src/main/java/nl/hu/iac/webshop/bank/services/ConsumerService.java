package nl.hu.iac.webshop.bank.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.iac.webshop.bank.DTO.BankConfirmationDTO;
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

    public void acknowledge(BankConfirmationDTO confirmation) {
        String routingKey = "bank.response";
        String json = convertToJson(confirmation);
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, json);
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
