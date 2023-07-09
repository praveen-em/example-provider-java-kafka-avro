package io.pactflow.example.kafka.kafka;

import io.pactflow.example.kafka.model.generated.ProductEvent;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class AvroMessageBuilder {

    private ProductEvent productAvroRecord = new ProductEvent();

    public AvroMessageBuilder withProduct(ProductEvent productEvent) {
        this.productAvroRecord = productEvent;
        return this;
    }

    public Message<ProductEvent> build() throws Exception {
        return MessageBuilder
            .withPayload(this.productAvroRecord)
            .setHeader(KafkaHeaders.TOPIC, "products-avro-v2")
            .setHeader(KafkaHeaders.KEY, productAvroRecord.getId())
            .setHeader("content-Type", "avro/binary; record=ProductEventAvro")
            .build();
    }    
}
