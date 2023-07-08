package io.pactflow.example.kafka.kafka;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import io.pactflow.example.kafka.model.generated.ProductEventAvro;

public class AvroMessageBuilder {

    private ProductEventAvro productAvroRecord = new ProductEventAvro();

    public AvroMessageBuilder withProduct(ProductEventAvro productEvent) {
//        this.productAvroRecord.setId(productEvent.getId());
//        this.productAvroRecord.setName(productEvent.getName());
//        this.productAvroRecord.setType(productEvent.getType());
//        this.productAvroRecord.setVersion(productEvent.getVersion());
//        this.productAvroRecord.setEvent(productEvent.getEvent());
        this.productAvroRecord = productEvent;
        return this;
    }

    public Message<io.pactflow.example.kafka.model.generated.ProductEventAvro> build() throws Exception {
        return MessageBuilder
            .withPayload(this.productAvroRecord)
            .setHeader(KafkaHeaders.TOPIC, "products-avro")
            .setHeader(KafkaHeaders.KEY, productAvroRecord.getId())
            .setHeader("content-Type", "avro/binary; record=ProductEventAvro")
            .build();
    }    
}
