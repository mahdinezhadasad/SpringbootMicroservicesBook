package bookservice.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
@Configuration
public class JmsConfig {
    
    public static final String BOOK_RESTOCK_REQUEST_QUEUE = "book-restock-request";
    public static final String NEW_BOOK_INVENTORY_QUEUE = "new-book-inventory";
    public static final String VALIDATE_BOOK_ORDER_QUEUE = "validate-book-order";
    public static final String VALIDATE_BOOK_ORDER_RESPONSE_QUEUE = "validate-book-order-response";
    
    @Bean // Serialize message content to JSON using TextMessage
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}