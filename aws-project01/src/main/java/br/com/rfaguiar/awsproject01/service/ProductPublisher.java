package br.com.rfaguiar.awsproject01.service;

import br.com.rfaguiar.awsproject01.model.Envelop;
import br.com.rfaguiar.awsproject01.model.EventType;
import br.com.rfaguiar.awsproject01.model.Product;
import br.com.rfaguiar.awsproject01.model.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductPublisher.class);

    private final AmazonSNS snsClient;
    private final Topic productEventsTopic;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductPublisher(AmazonSNS snsClient, Topic productEventsTopic, ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.productEventsTopic = productEventsTopic;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, EventType eventType, String username) {
        ProductEvent productEvent = new ProductEvent(product.getId(), product.getCode(), username);
        Envelop envelop = new Envelop(eventType);
        try {
            envelop.setData(objectMapper.writeValueAsString(productEvent));
            PublishResult publishResult = snsClient.publish(
                    productEventsTopic.getTopicArn(),
                    objectMapper.writeValueAsString(envelop)
            );
            LOGGER.info("Publish MessageId: {}", publishResult.getMessageId());
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to create product event, message: {}", e.getMessage());
        }
    }

}
