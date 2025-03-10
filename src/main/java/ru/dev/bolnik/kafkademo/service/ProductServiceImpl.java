package ru.dev.bolnik.kafkademo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.dev.bolnik.kafkademo.service.dto.CreateProductDto;
import ru.dev.bolnik.kafkademo.service.event.ProductCreatedEvent;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {

    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDto createProductDto) throws ExecutionException, InterruptedException {
        //TODO save to DB
        String productId = UUID.randomUUID().toString();
//        String productId = "DimaBolnik";

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                createProductDto.getTitle(), createProductDto.getPrice(), createProductDto.getQuantity());

        SendResult<String, ProductCreatedEvent> result = kafkaTemplate
                .send("product-created-events-topic", productId, productCreatedEvent).get();

        LOGGER.info("topic : {}", result.getRecordMetadata().topic());
        LOGGER.info("partition : {}", result.getRecordMetadata().partition());
        LOGGER.info("offset : {}", result.getRecordMetadata().offset());

        LOGGER.info("Return : {}", productId);

        return productId;
    }
}
