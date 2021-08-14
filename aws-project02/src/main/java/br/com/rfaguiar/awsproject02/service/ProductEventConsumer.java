package br.com.rfaguiar.awsproject02.service;

import br.com.rfaguiar.awsproject02.model.Envelope;
import br.com.rfaguiar.awsproject02.model.ProductEvent;
import br.com.rfaguiar.awsproject02.model.ProductEventLog;
import br.com.rfaguiar.awsproject02.model.SnsMessage;
import br.com.rfaguiar.awsproject02.repository.ProductEventLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.time.Duration;
import java.time.Instant;

@Service
public class ProductEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventConsumer.class);
    private final ObjectMapper objectMapper;
    private final ProductEventLogRepository productEventLogRepository;

    @Autowired
    public ProductEventConsumer(ObjectMapper objectMapper, ProductEventLogRepository productEventLogRepository) {
        this.objectMapper = objectMapper;
        this.productEventLogRepository = productEventLogRepository;
    }

    @JmsListener(destination ="${aws.sqs.queue.product.events.name}")
    public void receiveProductEvent(TextMessage textMessage) throws JMSException, JsonProcessingException {

        SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);
        Envelope envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);
        ProductEvent productEvent = objectMapper.readValue(envelope.getData(), ProductEvent.class);

        LOGGER.info("Product event received - MessageId: {} - Event: {} - ProductId: {}",
                snsMessage.getMessageId(),
                envelope.getEventType(),
                productEvent.getProductId());

        ProductEventLog productEventLog = buildProductEventLog(envelope, productEvent, snsMessage.getMessageId());
        productEventLogRepository.save(productEventLog);
    }

    private ProductEventLog buildProductEventLog(Envelope envelope, ProductEvent productEvent, String messageId) {
        long timestamp = Instant.now().toEpochMilli();

        ProductEventLog productEventLog = new ProductEventLog();
        productEventLog.setPk(productEvent.getCode());
        productEventLog.setSk(envelope.getEventType() + "_" + timestamp);
        productEventLog.setEventType(envelope.getEventType());
        productEventLog.setPoductId(productEvent.getProductId());
        productEventLog.setUsername(productEvent.getUsername());
        productEventLog.setTimestamp(timestamp);
        productEventLog.setTtl(Instant.now().plus(Duration.ofMinutes(10)).getEpochSecond());
        productEventLog.setMessageId(messageId);
        return productEventLog;
    }

}
